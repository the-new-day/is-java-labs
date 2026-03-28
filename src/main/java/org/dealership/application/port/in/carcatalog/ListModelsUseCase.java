package org.dealership.application.port.in.carcatalog;

import org.dealership.application.port.in.carcatalog.dto.ModelSummaryDto;

import java.util.List;

public interface ListModelsUseCase {
    Response execute(Request request);

    record Request() {}

    record Response(List<ModelSummaryDto> modelSummaryList) {}
}
