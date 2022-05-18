package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone implements Serializable {
    private static final long serialVersionUID = -6842951751629335005L;
    private int id;
    private String name;
}
