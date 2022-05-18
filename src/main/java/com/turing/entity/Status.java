package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable {
    private static final long serialVersionUID = -1254447662179019628L;
    private int id;
    private String name;
}
