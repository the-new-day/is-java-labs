package org.dealership.application.service.customorder;

import org.dealership.application.port.in.customorder.DeleteCustomOrderUseCase;
import org.dealership.application.port.out.persistence.CustomCarOrderRepository;
import org.dealership.domain.model.id.OrderId;
import org.dealership.domain.model.order.CustomCarOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteCustomOrderInteractorTest {
    @Mock
    private CustomCarOrderRepository customOrderRepository;

    @Test
    void shouldDeleteCustomOrder() {
        UUID orderIdValue = UUID.randomUUID();
        when(customOrderRepository.findById(new OrderId(orderIdValue)))
                .thenReturn(Optional.of(mock(CustomCarOrder.class)));

        DeleteCustomOrderInteractor interactor = new DeleteCustomOrderInteractor(customOrderRepository);
        var response = interactor.execute(new DeleteCustomOrderUseCase.Request(orderIdValue));

        assertNotNull(response);
        verify(customOrderRepository).deleteById(new OrderId(orderIdValue));
    }
}
