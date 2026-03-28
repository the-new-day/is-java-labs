package org.dealership.application.service.testdrive;

import org.dealership.application.port.in.testdrive.DeleteTestDriveRequestUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.application.service.testdrive.DeleteTestDriveRequestInteractor;
import org.dealership.domain.model.id.TestDriveRequestId;
import org.dealership.domain.model.testdrive.TestDriveRequest;
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
class DeleteTestDriveRequestInteractorTest {
    @Mock
    private TestDriveRequestRepository testDriveRequestRepository;

    @Test
    void shouldDeleteTestDriveRequest() {
        UUID requestIdValue = UUID.randomUUID();
        when(testDriveRequestRepository.findById(new TestDriveRequestId(requestIdValue)))
                .thenReturn(Optional.of(mock(TestDriveRequest.class)));

        DeleteTestDriveRequestInteractor interactor
                = new DeleteTestDriveRequestInteractor(testDriveRequestRepository);
        var response = interactor.execute(new DeleteTestDriveRequestUseCase.Request(requestIdValue));

        assertNotNull(response);
        verify(testDriveRequestRepository).deleteById(new TestDriveRequestId(requestIdValue));
    }
}
