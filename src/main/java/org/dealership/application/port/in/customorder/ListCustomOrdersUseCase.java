package org.dealership.application.port.in.customorder;

import org.dealership.application.port.in.customorder.dto.CustomOrderDto;

import java.util.List;
import java.util.UUID;

public interface ListCustomOrdersUseCase {
    Response execute(Request request);

    record Request(UUID clientIdFilter) {
        public Request() {
            this(null);
        }
    }

    record Response(List<CustomOrderDto> order) {}
}
