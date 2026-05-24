package org.dealership.grpc;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.dealership.application.port.in.inventory.dto.AvailableCarDto;
import org.dealership.domain.exception.EntityNotFoundException;
import org.dealership.domain.exception.StorageServiceUnavailableException;
import org.dealership.grpc.inventory.AvailableCar;
import org.dealership.grpc.inventory.GetAvailableCarRequest;
import org.dealership.grpc.inventory.GetAvailableCarResponse;
import org.dealership.grpc.inventory.InventoryServiceGrpc;
import org.dealership.grpc.inventory.ListAvailableCarsRequest;
import org.dealership.grpc.inventory.ListAvailableCarsResponse;
import org.dealership.infrastructure.grpc.client.StorageServiceGrpcAdapter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageServiceGrpcAdapterIT {

    private static final String SERVER_NAME = "test-adapter-server";
    private static final UUID TEST_CAR_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private static Server mockServer;
    private static ManagedChannel channel;
    private static StorageServiceGrpcAdapter adapter;

    @BeforeAll
    static void startServer() throws IOException {
        mockServer = InProcessServerBuilder.forName(SERVER_NAME)
                .directExecutor()
                .addService(new MockInventoryService())
                .build()
                .start();

        channel = InProcessChannelBuilder.forName(SERVER_NAME)
                .directExecutor()
                .build();

        adapter = new StorageServiceGrpcAdapter(channel, 5000);
    }

    @AfterAll
    static void stopServer() throws InterruptedException {
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        mockServer.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void listAvailableCars_returnsMappedCars() {
        List<AvailableCarDto> cars = adapter.listAvailableCars();

        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).id()).isEqualTo(TEST_CAR_ID);
        assertThat(cars.get(0).brandName()).isEqualTo("Toyota");
        assertThat(cars.get(0).modelName()).isEqualTo("Camry");
        assertThat(cars.get(0).color()).isEqualTo("WHITE");
        assertThat(cars.get(0).priceAmount()).isEqualByComparingTo("25000.00");
    }

    @Test
    void getAvailableCar_existingCar_returnsDto() {
        AvailableCarDto car = adapter.getAvailableCar(TEST_CAR_ID);

        assertThat(car.id()).isEqualTo(TEST_CAR_ID);
        assertThat(car.brandName()).isEqualTo("Toyota");
    }

    @Test
    void getAvailableCar_notFound_throwsEntityNotFoundException() {
        UUID notFoundId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        assertThrows(EntityNotFoundException.class, () -> adapter.getAvailableCar(notFoundId));
    }

    @Test
    void getAvailableCar_unavailable_throwsStorageServiceUnavailableException() {
        UUID unavailableId = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");

        assertThrows(StorageServiceUnavailableException.class, () -> adapter.getAvailableCar(unavailableId));
    }

    static class MockInventoryService extends InventoryServiceGrpc.InventoryServiceImplBase {

        @Override
        public void listAvailableCars(
                ListAvailableCarsRequest request,
                StreamObserver<ListAvailableCarsResponse> observer
        ) {
            observer.onNext(ListAvailableCarsResponse.newBuilder()
                    .addCars(testCar())
                    .build());
            observer.onCompleted();
        }

        @Override
        public void getAvailableCar(
                GetAvailableCarRequest request,
                StreamObserver<GetAvailableCarResponse> observer
        ) {
            String id = request.getId();

            if (id.equals("00000000-0000-0000-0000-000000000000")) {
                observer.onError(Status.NOT_FOUND.withDescription("Car not found").asRuntimeException());
                return;
            }
            if (id.equals("ffffffff-ffff-ffff-ffff-ffffffffffff")) {
                observer.onError(Status.UNAVAILABLE.withDescription("Service unavailable").asRuntimeException());
                return;
            }
            observer.onNext(GetAvailableCarResponse.newBuilder()
                    .setCar(testCar())
                    .build());
            observer.onCompleted();
        }

        private AvailableCar testCar() {
            return AvailableCar.newBuilder()
                    .setId(TEST_CAR_ID.toString())
                    .setBrandName("Toyota")
                    .setModelName("Camry")
                    .setColor("WHITE")
                    .setPriceAmount("25000.00")
                    .build();
        }
    }
}
