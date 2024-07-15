package com.generic.khatabook.room.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Floor")
public class Room {
    private int roomId;
    private List<Bad> bads;
    private List<Characteristics> characteristics;
}
