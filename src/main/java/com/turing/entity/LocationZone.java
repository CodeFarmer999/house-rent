package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationZone implements Serializable {
    private static final long serialVersionUID = 6094332294619917905L;
    private int id;
    private int locationId;
    private int zoneId;
}
