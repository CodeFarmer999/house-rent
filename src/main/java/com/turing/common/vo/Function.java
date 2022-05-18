package com.turing.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 9:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Function implements Serializable {
    private static final long serialVersionUID = 4766457761659785534L;
    private String name;
    private String privilegeName;
}
