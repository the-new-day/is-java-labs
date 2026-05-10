package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.UpdateTestDriveRequestUseCase;
import org.dealership.application.port.in.testdrive.dto.NewTestDriveRequestDto;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.testdrive.TestDriveRequest;
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
    @Mock
    private TestDriveRequestMapper testDriveRequestMapper;

    @Test
    void shouldUpdateTestDriveRequest() {
        UUID requestIdValue = UUID.randomUUID();
        UUID carIdValue = UUID.randomUUID();
        TestDriveRequest existing = ServiceTestData.testDriveRequest(
                requestIdValue,
                UUID.randomUUID(),
                carIdValue,
                LocalDateTime.now()
        );

        when(testDriveRequestRepository.findById(new TestDriveRequestId(requestIdValue)))
                .thenReturn(Optional.of(existing));

        UpdateTestDriveRequestInteractor interactor = new UpdateTestDriveRequestInteractor(
                testDriveRequestRepository, testDriveRequestMapper);
        var response = interactor.execute(new UpdateTestDriveRequestUseCase.Request(
                requestIdValue,
                new NewTestDriveRequestDto(UUID.randomUUID(), carIdValue, LocalDateTime.now())
        ));

        assertNotNull(response);
        verify(testDriveRequestRepository).save(org.mockito.Mockito.any());
    }
}
