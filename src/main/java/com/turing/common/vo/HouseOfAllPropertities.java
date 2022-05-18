package com.turing.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/12 16:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseOfAllPropertities implements Serializable {
    private static final long serialVersionUID = -1911575238055115687L;
    private int id;
    private double rent;
    private double area;
    private Boolean isHire;
    private int ownerId;
    private int middlemanId;
    private String typeName;
    private String locationName;
}
