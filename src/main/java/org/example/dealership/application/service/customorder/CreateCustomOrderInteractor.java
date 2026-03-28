package org.example.dealership.application.service.customorder;

import org.example.dealership.application.mapping.ConfigurationMapper;
import org.example.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.example.dealership.application.port.in.common.dto.ConfigurationDto;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.car.CarModel;
import org.example.dealership.domain.model.configuration.Configuration;
import org.example.dealership.domain.model.id.CarModelId;
import org.example.dealership.domain.model.id.UserId;
import org.example.dealership.domain.model.order.CustomCarOrder;
import org.example.dealership.domain.model.order.state.CustomCarOrderStatus;

public class CreateCustomOrderInteractor implements CreateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CarModelRepository carModelRepository;

    public CreateCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CarModelRepository carModelRepository
    ) {
        this.customOrderRepository = customOrderRepository;
        this.carModelRepository = carModelRepository;
    }

    @Override
    public Response execute(Request request) {
        ConfigurationDto configurationDto = request.configuration();
        CarModelId modelId = new CarModelId(configurationDto.carModel().id());
        CarModel model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        Configuration configuration = ConfigurationMapper.mapFromDto(configurationDto, model);
        CustomCarOrder order = new CustomCarOrder(
                customOrderRepository.nextId(),
                new UserId(request.clientId()),
                new UserId(request.managerId()),
                configuration,
                CustomCarOrderStatus.PLACED
        );
        customOrderRepository.save(order);
        return new Response(order.getId().value());
    }
}
