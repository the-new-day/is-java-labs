package org.dealership.infrastructure.config;

import org.dealership.application.mapper.AssemblyOrderMapper;
import org.dealership.application.port.in.assembly.CreateAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.DeleteAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.GetAssemblyOrderUseCase;
import org.dealership.application.port.in.assembly.ListAssemblyOrdersUseCase;
import org.dealership.application.port.in.assembly.UpdateAssemblyOrderUseCase;
import org.dealership.application.port.out.persistence.AssemblyOrderRepository;
import org.dealership.application.service.assembly.CreateAssemblyOrderInteractor;
import org.dealership.application.service.assembly.DeleteAssemblyOrderInteractor;
import org.dealership.application.service.assembly.GetAssemblyOrderInteractor;
import org.dealership.application.service.assembly.ListAssemblyOrdersInteractor;
import org.dealership.application.service.assembly.UpdateAssemblyOrderInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssemblyOrderUseCaseConfig {

    @Bean
    public CreateAssemblyOrderUseCase createAssemblyOrderUseCase(
            AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        return new CreateAssemblyOrderInteractor(repository, mapper);
    }

    @Bean
    public GetAssemblyOrderUseCase getAssemblyOrderUseCase(
            AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        return new GetAssemblyOrderInteractor(repository, mapper);
    }

    @Bean
    public ListAssemblyOrdersUseCase listAssemblyOrdersUseCase(
            AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        return new ListAssemblyOrdersInteractor(repository, mapper);
    }

    @Bean
    public UpdateAssemblyOrderUseCase updateAssemblyOrderUseCase(
            AssemblyOrderRepository repository, AssemblyOrderMapper mapper) {
        return new UpdateAssemblyOrderInteractor(repository, mapper);
    }

    @Bean
    public DeleteAssemblyOrderUseCase deleteAssemblyOrderUseCase(AssemblyOrderRepository repository) {
        return new DeleteAssemblyOrderInteractor(repository);
    }
}
