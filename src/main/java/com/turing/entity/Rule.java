package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:33
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule implements Serializable {
    private static final long serialVersionUID = -8063257828372130166L;
    private int id;
    private String name;
}
