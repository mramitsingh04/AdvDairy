package com.generic.khatabook.room.service.model;

import java.util.List;

public record FloorDTO(int id, List<RoomDTO> room) {
}
