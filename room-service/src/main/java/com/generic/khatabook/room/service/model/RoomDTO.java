package com.generic.khatabook.room.service.model;

import java.util.List;
import java.util.stream.Collector;

public record RoomDTO(int roomId, List<BadDTO> bads, List<Characteristics> characteristics) {
}
