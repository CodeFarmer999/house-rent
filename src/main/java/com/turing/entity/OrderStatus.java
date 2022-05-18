package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus implements Serializable {
    private static final long serialVersionUID = 2702605982251849419L;
    private int id;
    private String orderId;
    private int statusId;
}
