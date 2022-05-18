package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House implements Serializable {
    private static final long serialVersionUID = 1539619560817986477L;
    private int id;
    /**
     * 租金
     */
    private double rent;
    /**
     * 面积
     */
    private double area;
    /**
     * 是否已租赁
     */
    private Boolean isHire;
    /**
     * 房主id
     */
    private int ownerId;
    /**
     * 中介id
     */
    private int middlemanId;

    public House(double rent, double area, int ownerId) {
        this.rent = rent;
        this.area = area;
        this.ownerId = ownerId;
    }

    public House(int id, double rent, double area, int ownerId) {
        this.id = id;
        this.rent = rent;
        this.area = area;
        this.ownerId = ownerId;
    }
}
