package org.dealership.infrastructure.config;

import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.dealership.infrastructure.grpc.client.StorageServiceGrpcAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("storage-service")
    private Channel storageServiceChannel;

    @Bean
    public StorageServiceGrpcAdapter storageServiceGrpcAdapter(
            @Value("${dealership.grpc.client.timeout-ms:5000}") long timeoutMs
    ) {
        return new StorageServiceGrpcAdapter(storageServiceChannel, timeoutMs);
    }
}
