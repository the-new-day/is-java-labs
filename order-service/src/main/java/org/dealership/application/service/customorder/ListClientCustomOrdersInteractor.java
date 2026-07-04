package org.dealership.application.service.customorder;

import org.dealership.application.mapper.BaseIdMapper;
import org.dealership.application.mapper.CustomOrderMapper;
import org.dealership.application.port.in.customorder.ListClientCustomOrdersUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.model.order.CustomCarOrder;

import java.util.List;

public class ListClientCustomOrdersInteractor implements ListClientCustomOrdersUseCase {
    private final CustomCarOrderRepository customOrderRepository;
    private final BaseIdMapper idMapper;
    private final CustomOrderMapper customOrderMapper;

    public ListClientCustomOrdersInteractor(
            CustomCarOrderRepository customOrderRepository,
            BaseIdMapper idMapper,
            CustomOrderMapper customOrderMapper
    ) {
        this.customOrderRepository = customOrderRepository;
        this.idMapper = idMapper;
        this.customOrderMapper = customOrderMapper;
    }

    @Override
    public Response execute(Request request) {
        List<CustomCarOrder> orders = customOrderRepository.findByClientId(idMapper.toUserId(request.clientId()));
        return new Response(orders.stream().map(customOrderMapper::toDto).toList());
    }
}
