package org.dealership.application.service.customorder;

import org.dealership.application.mapper.ConfigurationMapper;
import org.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.security.ManagerProvider;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.user.UserSelectionStrategy;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;

import java.util.List;

public class CreateCustomOrderInteractor implements CreateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CarModelRepository carModelRepository;
    private final ManagerProvider managerProvider;
    private final UserSelectionStrategy userSelectionStrategy;
    private final ConfigurationMapper configurationMapper;

    public CreateCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CarModelRepository carModelRepository,
            ManagerProvider managerProvider,
            UserSelectionStrategy userSelectionStrategy,
            ConfigurationMapper configurationMapper
    ) {
        this.customOrderRepository = customOrderRepository;
        this.carModelRepository = carModelRepository;
        this.managerProvider = managerProvider;
        this.userSelectionStrategy = userSelectionStrategy;
        this.configurationMapper = configurationMapper;
    }

    @Override
    public Response execute(Request request) {
        ConfigurationDto configurationDto = request.configuration();
        CarModelId modelId = new CarModelId(configurationDto.carModel().id());
        carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        Configuration configuration = configurationMapper.toDomain(configurationDto);

        List<UserId> managers = managerProvider.listManagerIds();

        CustomCarOrder order = new CustomCarOrder(
                customOrderRepository.nextId(),
                new UserId(request.clientId()),
                userSelectionStrategy.selectUser(managers),
                configuration,
                CustomCarOrderStatus.PLACED
        );
        customOrderRepository.save(order);
        return new Response(order.getId().value());
    }
}
