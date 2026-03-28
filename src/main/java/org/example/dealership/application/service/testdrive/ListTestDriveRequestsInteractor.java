package org.example.dealership.application.service.testdrive;

import org.example.dealership.application.mapping.StockOrderMapper;
import org.example.dealership.application.mapping.TestDriveRequestMapper;
import org.example.dealership.application.port.in.stockorder.ListStockOrdersUseCase;
import org.example.dealership.application.port.in.testdrive.GetTestDriveRequestUseCase;
import org.example.dealership.application.port.in.testdrive.ListTestDriveRequestsUseCase;
import org.example.dealership.application.port.out.persistence.TestDriveRequestRepository;
import org.example.dealership.domain.exception.EntityNotFoundException;
import org.example.dealership.domain.model.id.TestDriveRequestId;

public class ListTestDriveRequestsInteractor implements ListTestDriveRequestsUseCase {
    private final TestDriveRequestRepository testDriveRequestRepository;

    public ListTestDriveRequestsInteractor(TestDriveRequestRepository testDriveRequestRepository) {
        this.testDriveRequestRepository = testDriveRequestRepository;
    }

    @Override
    public Response execute(Request request) {
        return new Response(
                testDriveRequestRepository.findAll().stream()
                        .map(TestDriveRequestMapper::mapToDto)
                        .toList()
        );
    }
}
