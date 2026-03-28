package org.dealership.application.service.testdrive;

import org.dealership.application.port.in.testdrive.GetTestDriveRequestUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTestDriveRequestInteractorTest {
    @Mock
    private TestDriveRequestRepository testDriveRequestRepository;

    @Test
    void shouldGetTestDriveRequest() {
        UUID requestIdValue = UUID.randomUUID();
        TestDriveRequest request = ServiceTestData.testDriveRequest(
                requestIdValue,
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now()
        );

        when(testDriveRequestRepository.findById(new TestDriveRequestId(requestIdValue)))
                .thenReturn(Optional.of(request));

        GetTestDriveRequestInteractor interactor = new GetTestDriveRequestInteractor(testDriveRequestRepository);
        var response = interactor.execute(new GetTestDriveRequestUseCase.Request(requestIdValue));

        assertEquals(requestIdValue, response.testDriveRequest().id());
    }
}
