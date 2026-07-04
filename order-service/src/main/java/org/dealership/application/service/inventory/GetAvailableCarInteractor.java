package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.GetAvailableCarUseCase;
import org.dealership.application.port.out.inventory.AvailableCarsPort;

public class GetAvailableCarInteractor implements GetAvailableCarUseCase {

    private final AvailableCarsPort availableCarsPort;

    public GetAvailableCarInteractor(AvailableCarsPort availableCarsPort) {
        this.availableCarsPort = availableCarsPort;
    }

    @Override
    public Response execute(Request request) {
        return new Response(availableCarsPort.getAvailableCar(request.id()));
    }
}
