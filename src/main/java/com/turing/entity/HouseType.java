package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseType implements Serializable {
    private static final long serialVersionUID = -3801503035159941093L;
    private int id;
    private int houseId;
    private int typeId;
}
