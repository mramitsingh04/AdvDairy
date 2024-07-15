package com.generic.khatabook.room.service.model;

import java.util.List;

public record HostalDTO(int id, String name, List<FloorDTO> floors) {

}
