package org.dealership.grpc;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import org.dealership.AbstractControllerIT;
import org.dealership.grpc.inventory.GetAvailableCarRequest;
import org.dealership.grpc.inventory.GetAvailableCarResponse;
import org.dealership.grpc.inventory.InventoryServiceGrpc;
import org.dealership.grpc.inventory.ListAvailableCarsRequest;
import org.dealership.grpc.inventory.ListAvailableCarsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryGrpcServiceIT extends AbstractControllerIT {

    static final UUID SEED_CAR_ID = UUID.fromString("00000000-0000-0000-0000-000000000501");

    private ManagedChannel channel;
    private InventoryServiceGrpc.InventoryServiceBlockingStub stub;

    @BeforeEach
    void setupChannel() {
        channel = InProcessChannelBuilder.forName("test-storage-service")
                .directExecutor()
                .build();
        stub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void teardownChannel() throws InterruptedException {
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void listAvailableCars_returnsSeededCars() {
        ListAvailableCarsResponse response = stub.listAvailableCars(ListAvailableCarsRequest.getDefaultInstance());

        assertThat(response.getCarsList()).isNotEmpty();
        assertThat(response.getCarsList()).allSatisfy(car -> {
            assertThat(car.getId()).isNotBlank();
            assertThat(car.getBrandName()).isNotBlank();
            assertThat(car.getModelName()).isNotBlank();
            assertThat(car.getColor()).isNotBlank();
            assertThat(car.getPriceAmount()).isNotBlank();
        });
    }

    @Test
    void listAvailableCars_responseContainsSeededCar() {
        ListAvailableCarsResponse response = stub.listAvailableCars(ListAvailableCarsRequest.getDefaultInstance());

        assertThat(response.getCarsList())
                .anyMatch(car -> car.getId().equals(SEED_CAR_ID.toString()));
    }

    @Test
    void getAvailableCar_existingCar_returnsCar() {
        GetAvailableCarResponse response = stub.getAvailableCar(
                GetAvailableCarRequest.newBuilder().setId(SEED_CAR_ID.toString()).build()
        );

        assertThat(response.getCar().getId()).isEqualTo(SEED_CAR_ID.toString());
        assertThat(response.getCar().getBrandName()).isNotBlank();
        assertThat(response.getCar().getModelName()).isNotBlank();
    }

    @Test
    void getAvailableCar_nonExistingCar_returnsNotFound() {
        String randomId = UUID.randomUUID().toString();

        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () ->
                stub.getAvailableCar(GetAvailableCarRequest.newBuilder().setId(randomId).build())
        );

        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.NOT_FOUND);
    }

    @Test
    void getAvailableCar_invalidUuid_returnsInvalidArgument() {
        StatusRuntimeException exception = assertThrows(StatusRuntimeException.class, () ->
                stub.getAvailableCar(GetAvailableCarRequest.newBuilder().setId("not-a-uuid").build())
        );

        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.INVALID_ARGUMENT);
    }
}
