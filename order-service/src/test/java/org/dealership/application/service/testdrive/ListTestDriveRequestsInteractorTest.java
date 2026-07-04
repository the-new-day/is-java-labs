package org.dealership.application.service.testdrive;

import org.dealership.application.mapper.TestDriveRequestMapper;
import org.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.dealership.application.service.ServiceTestData;
import org.dealership.domain.model.testdrive.TestDriveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTestDriveRequestsInteractorTest {
    @Mock
    private TestDriveRequestRepository testDriveRequestRepository;
    @Mock
    private TestDriveRequestMapper testDriveRequestMapper;

    @Test
    void shouldListTestDriveRequests() {
        TestDriveRequest request = ServiceTestData.testDriveRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now()
        );
        when(testDriveRequestRepository.findAll()).thenReturn(List.of(request));

        ListTestDriveRequestsInteractor interactor
                = new ListTestDriveRequestsInteractor(testDriveRequestRepository, testDriveRequestMapper);
        var response = interactor.execute(new ListTestDriveRequestsUseCase.Request());

        assertEquals(1, response.testDriveRequest().size());
    }
}
