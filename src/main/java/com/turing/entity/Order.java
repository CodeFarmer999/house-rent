package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -2171371710194430301L;
    private String id;
    private int userId;
    private int allowTime;
    private double rentMoney;
    private boolean rentMoneyIsPay;
    private double middleMoney;
    private boolean middleMoneyIsPay;
}
