package org.example.dealership.application.service.testdrive;

import org.example.dealership.application.port.in.testdrive.UpdateTestDriveRequestUseCase;
import org.example.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.example.dealership.application.service.ServiceTestData;
import org.example.dealership.domain.model.id.TestDriveRequestId;
import org.example.dealership.domain.model.testdrive.TestDriveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTestDriveRequestInteractorTest {
    @Mock
    private TestDriveRequestRepository testDriveRequestRepository;

    @Test
    void shouldUpdateTestDriveRequest() {
        UUID requestIdValue = UUID.randomUUID();
        UUID carIdValue = UUID.randomUUID();
        TestDriveRequest request = ServiceTestData.testDriveRequest(
                requestIdValue,
                UUID.randomUUID(),
                carIdValue,
                LocalDateTime.now()
        );

        when(testDriveRequestRepository.findById(new TestDriveRequestId(requestIdValue)))
                .thenReturn(Optional.of(request));

        UpdateTestDriveRequestInteractor interactor = new UpdateTestDriveRequestInteractor(
                testDriveRequestRepository);
        var response = interactor.execute(new UpdateTestDriveRequestUseCase.Request(
                ServiceTestData.testDriveRequestDto(
                        requestIdValue, UUID.randomUUID(), carIdValue, LocalDateTime.now()
                )
        ));

        assertNotNull(response);
        verify(testDriveRequestRepository).save(org.mockito.Mockito.any());
    }
}
