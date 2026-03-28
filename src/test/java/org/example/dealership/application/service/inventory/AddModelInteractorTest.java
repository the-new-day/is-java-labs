package org.example.dealership.application.service.inventory;

import org.example.dealership.application.port.in.inventory.AddModelUseCase;
import org.example.dealership.application.port.in.inventory.dto.NewModelDto;
import org.example.dealership.application.port.out.persistence.BrandRepository;
import org.example.dealership.application.port.out.persistence.CarModelRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.car.Brand;
import org.example.dealership.domain.model.id.BrandId;
import org.example.dealership.domain.model.id.CarModelId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddModelInteractorTest {
    @Mock
    private CarModelRepository carModelRepository;
    @Mock
    private BrandRepository brandRepository;

    @Test
    void shouldAddModel() {
        UUID brandIdValue = UUID.randomUUID();
        Brand brand = ServiceTestData.brand(brandIdValue);

        when(brandRepository.findById(new BrandId(brandIdValue))).thenReturn(Optional.of(brand));
        when(carModelRepository.nextId()).thenReturn(new CarModelId(UUID.randomUUID()));

        AddModelInteractor interactor = new AddModelInteractor(carModelRepository, brandRepository);
        NewModelDto newModel = ServiceTestData.newModelDto(brandIdValue);
        var response = interactor.execute(new AddModelUseCase.Request(newModel));

        assertNotNull(response.id());
        verify(carModelRepository).save(org.mockito.Mockito.any());
    }
}
