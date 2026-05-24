package org.dealership.infrastructure.grpc.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.dealership.application.port.in.inventory.GetInventoryCarUseCase;
import org.dealership.application.port.in.inventory.ListInventoryCarsUseCase;
import org.dealership.application.port.in.inventory.dto.CarSummaryDto;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.grpc.inventory.AvailableCar;
import org.dealership.grpc.inventory.GetAvailableCarRequest;
import org.dealership.grpc.inventory.GetAvailableCarResponse;
import org.dealership.grpc.inventory.InventoryServiceGrpc;
import org.dealership.grpc.inventory.ListAvailableCarsRequest;
import org.dealership.grpc.inventory.ListAvailableCarsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(InventoryGrpcService.class);

    private final ListInventoryCarsUseCase listInventoryCarsUseCase;
    private final GetInventoryCarUseCase getInventoryCarUseCase;

    public InventoryGrpcService(
            ListInventoryCarsUseCase listInventoryCarsUseCase,
            GetInventoryCarUseCase getInventoryCarUseCase
    ) {
        this.listInventoryCarsUseCase = listInventoryCarsUseCase;
        this.getInventoryCarUseCase = getInventoryCarUseCase;
    }

    @Override
    public void listAvailableCars(
            ListAvailableCarsRequest request,
            StreamObserver<ListAvailableCarsResponse> responseObserver
    ) {
        log.info("gRPC listAvailableCars called");
        try {
            List<CarSummaryDto> cars = listInventoryCarsUseCase
                    .execute(new ListInventoryCarsUseCase.Request())
                    .carSummaryList();

            List<AvailableCar> grpcCars = cars.stream().map(this::toProto).toList();

            responseObserver.onNext(ListAvailableCarsResponse.newBuilder()
                    .addAllCars(grpcCars)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error in listAvailableCars", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getAvailableCar(
            GetAvailableCarRequest request,
            StreamObserver<GetAvailableCarResponse> responseObserver
    ) {
        log.info("gRPC getAvailableCar called for id={}", request.getId());
        try {
            UUID carId = UUID.fromString(request.getId());
            GetInventoryCarUseCase.Response result = getInventoryCarUseCase
                    .execute(new GetInventoryCarUseCase.Request(carId));

            AvailableCar grpcCar = AvailableCar.newBuilder()
                    .setId(result.carDetails().id().toString())
                    .setBrandName(result.carDetails().configuration().carModel().brand().name())
                    .setModelName(result.carDetails().configuration().carModel().modelName())
                    .setColor(result.carDetails().color().name())
                    .setPriceAmount(result.carDetails().price().amount().toString())
                    .build();

            responseObserver.onNext(GetAvailableCarResponse.newBuilder().setCar(grpcCar).build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid car id").asRuntimeException());
        } catch (Exception e) {
            log.error("Error in getAvailableCar", e);
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    private AvailableCar toProto(CarSummaryDto car) {
        return AvailableCar.newBuilder()
                .setId(car.id().toString())
                .setBrandName(car.model().brand().name())
                .setModelName(car.model().modelName())
                .setColor(car.color().name())
                .setPriceAmount(car.price().amount().toString())
                .build();
    }
}
