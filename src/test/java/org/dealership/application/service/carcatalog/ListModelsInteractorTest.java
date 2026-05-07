package org.dealership.application.service.carcatalog;

import org.dealership.application.mapper.CarModelMapper;
import org.dealership.application.port.in.carcatalog.ListModelsUseCase;
import org.dealership.application.port.out.persistence.CarModelRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.car.Brand;
import org.dealership.domain.model.car.CarModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListModelsInteractorTest {
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private CarModelMapper carModelMapper;

    @Test
    void shouldListModels() {
        Brand brand = ServiceTestData.brand(UUID.randomUUID());
        CarModel model = ServiceTestData.carModel(UUID.randomUUID(), brand);

        when(carModelRepository.findAll()).thenReturn(List.of(model));

        ListModelsInteractor interactor = new ListModelsInteractor(carModelRepository, carModelMapper);
        var response = interactor.execute(new ListModelsUseCase.Request());

        assertEquals(1, response.modelSummaryList().size());
        verify(carModelRepository).findAll();
    }
}
