package com.turing.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/14 10:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseOfArea implements Serializable {
    private static final long serialVersionUID = -8941916819659635582L;
    private double area;
}
