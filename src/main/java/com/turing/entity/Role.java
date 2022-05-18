package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = -8547522845870299298L;
    private int id;
    private String name;
}
