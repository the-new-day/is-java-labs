package org.dealership.infrastructure.grpc.client;

import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.dealership.application.port.in.inventory.dto.AvailableCarDto;
import org.dealership.application.port.out.inventory.AvailableCarsPort;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.exception.StorageServiceUnavailableException;
import org.dealership.grpc.inventory.AvailableCar;
import org.dealership.grpc.inventory.GetAvailableCarRequest;
import org.dealership.grpc.inventory.GetAvailableCarResponse;
import org.dealership.grpc.inventory.InventoryServiceGrpc;
import org.dealership.grpc.inventory.ListAvailableCarsRequest;
import org.dealership.grpc.inventory.ListAvailableCarsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StorageServiceGrpcAdapter implements AvailableCarsPort {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceGrpcAdapter.class);

    private final InventoryServiceGrpc.InventoryServiceBlockingStub stub;
    private final long timeoutMs;

    public StorageServiceGrpcAdapter(Channel channel, long timeoutMs) {
        this.stub = InventoryServiceGrpc.newBlockingStub(channel);
        this.timeoutMs = timeoutMs;
    }

    @Override
    public List<AvailableCarDto> listAvailableCars() {
        log.info("Calling StorageService gRPC: listAvailableCars");
        try {
            ListAvailableCarsResponse response = stub
                    .withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS)
                    .listAvailableCars(ListAvailableCarsRequest.getDefaultInstance());
            return response.getCarsList().stream().map(this::toDto).toList();
        } catch (StatusRuntimeException e) {
            log.error("StorageService gRPC error on listAvailableCars: {}", e.getStatus());
            throw new StorageServiceUnavailableException("StorageService unavailable: " + e.getStatus().getDescription());
        }
    }

    @Override
    public AvailableCarDto getAvailableCar(UUID id) {
        log.info("Calling StorageService gRPC: getAvailableCar id={}", id);
        try {
            GetAvailableCarResponse response = stub
                    .withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS)
                    .getAvailableCar(GetAvailableCarRequest.newBuilder().setId(id.toString()).build());
            return toDto(response.getCar());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new EntityNotFoundException("Car not found: " + id);
            }
            log.error("StorageService gRPC error on getAvailableCar: {}", e.getStatus());
            throw new StorageServiceUnavailableException("StorageService unavailable: " + e.getStatus().getDescription());
        }
    }

    private AvailableCarDto toDto(AvailableCar car) {
        return new AvailableCarDto(
                UUID.fromString(car.getId()),
                car.getBrandName(),
                car.getModelName(),
                car.getColor(),
                new BigDecimal(car.getPriceAmount())
        );
    }
}
