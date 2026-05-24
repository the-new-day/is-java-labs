package org.dealership.application.service.inventory;

import org.dealership.application.port.in.inventory.ListAvailableCarsUseCase;
import org.dealership.application.port.out.inventory.AvailableCarsPort;

public class ListAvailableCarsInteractor implements ListAvailableCarsUseCase {

    private final AvailableCarsPort availableCarsPort;

    public ListAvailableCarsInteractor(AvailableCarsPort availableCarsPort) {
        this.availableCarsPort = availableCarsPort;
    }

    @Override
    public Response execute(Request request) {
        return new Response(availableCarsPort.listAvailableCars());
    }
}
