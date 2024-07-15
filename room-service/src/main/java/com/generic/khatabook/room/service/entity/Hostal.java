package com.generic.khatabook.room.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Hostal")
public final class Hostal {
    private int id;
    private String name;
    private List<Floor> floors;
}
