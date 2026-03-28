package org.dealership.application.service.customorder;

import org.dealership.application.mapping.ConfigurationMapper;
import org.dealership.application.port.in.customorder.CreateCustomOrderUseCase;
import org.dealership.application.port.in.common.dto.ConfigurationDto;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.application.port.out.persistence.UserRepository;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.model.car.CarModel;
import org.dealership.domain.model.configuration.Configuration;
import org.dealership.domain.model.id.CarModelId;
import org.dealership.domain.model.id.UserId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.dealership.domain.model.order.UserSelectionStrategy;
import org.dealership.domain.model.order.state.CustomCarOrderStatus;
import org.dealership.domain.model.user.User;
import org.dealership.domain.model.user.UserRole;

import java.util.List;

public class CreateCustomOrderInteractor implements CreateCustomOrderUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final CarModelRepository carModelRepository;
    private final UserRepository userRepository;
    private final UserSelectionStrategy userSelectionStrategy;

    public CreateCustomOrderInteractor(
            CustomCarOrderRepository customOrderRepository,
            CarModelRepository carModelRepository,
            UserRepository userRepository,
            UserSelectionStrategy userSelectionStrategy
    ) {
        this.customOrderRepository = customOrderRepository;
        this.carModelRepository = carModelRepository;
        this.userRepository = userRepository;
        this.userSelectionStrategy = userSelectionStrategy;
    }

    @Override
    public Response execute(Request request) {
        ConfigurationDto configurationDto = request.configuration();
        CarModelId modelId = new CarModelId(configurationDto.carModel().id());
        CarModel model = carModelRepository.findById(modelId)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found: " + modelId));
        Configuration configuration = ConfigurationMapper.mapFromDto(configurationDto, model);

        List<UserId> managers = userRepository.findByRole(UserRole.MANAGER)
                .stream()
                .map(User::getId)
                .toList();

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
