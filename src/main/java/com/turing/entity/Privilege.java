package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilege implements Serializable {
    private static final long serialVersionUID = 5675892131563908170L;
    private int id;
    private String name;
    private String module;
}
