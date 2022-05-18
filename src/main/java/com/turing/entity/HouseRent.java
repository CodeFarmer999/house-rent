package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRent implements Serializable {
    private static final long serialVersionUID = -1684363193690151130L;
    private int id;
    private int houseId;
    private int userId;
    private int leftTime;
}
