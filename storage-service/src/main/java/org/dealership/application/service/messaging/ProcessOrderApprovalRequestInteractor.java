package org.dealership.application.service.messaging;

import org.dealership.application.port.in.messaging.ProcessOrderApprovalRequestUseCase;
import org.dealership.application.port.out.messaging.OrderResponseEventPort;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CarRepository;
import org.dealership.domain.model.assembly.AssemblyOrder;
import org.dealership.domain.model.assembly.AssemblyOrderStatus;
import org.dealership.domain.model.assembly.PartRequirement;
import org.dealership.domain.model.assembly.SourceOrderType;
import org.dealership.domain.model.car.Car;
import org.dealership.domain.model.id.AssemblyOrderId;
import org.dealership.domain.model.id.CarId;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.events.OrderApprovedEvent;
import org.dealership.events.OrderRejectedEvent;
import org.dealership.events.OrderSentForApprovalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProcessOrderApprovalRequestInteractor implements ProcessOrderApprovalRequestUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessOrderApprovalRequestInteractor.class);

    private final AssemblyOrderRepository assemblyOrderRepository;
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final OrderResponseEventPort responseEventPort;

    public ProcessOrderApprovalRequestInteractor(
            AssemblyOrderRepository assemblyOrderRepository,
            CarRepository carRepository,
            CarModelRepository carModelRepository,
            OrderResponseEventPort responseEventPort
    ) {
        this.assemblyOrderRepository = assemblyOrderRepository;
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
        this.responseEventPort = responseEventPort;
    }

    @Override
    public void execute(OrderSentForApprovalEvent event) {
        log.info("Processing OrderSentForApproval: orderId={}, traceId={}, type={}",
                event.orderId(), event.traceId(), event.orderType());
        try {
            switch (event.orderType()) {
                case "STOCK" -> processStock(event);
                case "CUSTOM" -> processCustom(event);
                default -> reject(event, "Unknown orderType: " + event.orderType());
            }
        } catch (RuntimeException e) {
            log.error("Approval processing failed: orderId={}, traceId={}",
                    event.orderId(), event.traceId(), e);
            reject(event, "Processing error: " + e.getMessage());
        }
    }

    private void processStock(OrderSentForApprovalEvent event) {
        if (event.carId() == null) {
            reject(event, "carId is required for STOCK order");
            return;
        }
        Optional<Car> car = carRepository.findById(new CarId(event.carId()));
        if (car.isEmpty()) {
            reject(event, "Car not found: " + event.carId());
            return;
        }
        List<PartRequirement> parts = car.get().getConfiguration()
                .getComponentVariantSelection()
                .getVariants()
                .stream()
                .map(v -> new PartRequirement(v.getId().value(), 1))
                .toList();
        if (parts.isEmpty()) {
            reject(event, "Car has no components: " + event.carId());
            return;
        }
        AssemblyOrder order = createAssemblyOrder(event, SourceOrderType.IN_STOCK,
                new CarId(event.carId()), null, parts);
        approve(event, order);
    }

    private void processCustom(OrderSentForApprovalEvent event) {
        if (event.carModelId() == null) {
            reject(event, "carModelId is required for CUSTOM order");
            return;
        }
        if (event.requiredParts() == null || event.requiredParts().isEmpty()) {
            reject(event, "requiredParts is required for CUSTOM order");
            return;
        }
        if (carModelRepository.findById(new CarModelId(event.carModelId())).isEmpty()) {
            reject(event, "CarModel not found: " + event.carModelId());
            return;
        }
        List<PartRequirement> parts = event.requiredParts().stream()
                .map(p -> new PartRequirement(p.partId(), p.quantity()))
                .toList();
        AssemblyOrder order = createAssemblyOrder(event, SourceOrderType.CUSTOM,
                null, new CarModelId(event.carModelId()), parts);
        approve(event, order);
    }

    private AssemblyOrder createAssemblyOrder(
            OrderSentForApprovalEvent event,
            SourceOrderType type,
            CarId carId,
            CarModelId carModelId,
            List<PartRequirement> parts
    ) {
        AssemblyOrderId id = assemblyOrderRepository.nextId();
        AssemblyOrder order = new AssemblyOrder(
                id,
                UUID.fromString(event.orderId()),
                type,
                carId,
                carModelId,
                parts,
                AssemblyOrderStatus.CREATED,
                null
        );
        assemblyOrderRepository.save(order);
        return order;
    }

    private void approve(OrderSentForApprovalEvent event, AssemblyOrder order) {
        OrderApprovedEvent approved = new OrderApprovedEvent(
                event.orderId(),
                event.traceId(),
                order.getId().value()
        );
        responseEventPort.publishApproved(approved);
        log.info("AssemblyOrder created: orderId={}, traceId={}, assemblyOrderId={}",
                event.orderId(), event.traceId(), order.getId().value());
    }

    private void reject(OrderSentForApprovalEvent event, String reason) {
        OrderRejectedEvent rejected = new OrderRejectedEvent(
                event.orderId(),
                event.traceId(),
                reason
        );
        responseEventPort.publishRejected(rejected);
        log.warn("Order rejected: orderId={}, traceId={}, reason={}",
                event.orderId(), event.traceId(), reason);
    }
}
