package com.github.v1ctu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Rank {

    private String id;
    private String name;
    private double price;
    private int position;

}
