package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseLocation implements Serializable {
    private static final long serialVersionUID = -2620043140417726081L;
    private int id;
    private int houseId;
    private int locationId;
}
