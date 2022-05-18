package com.turing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Turing
 * @date 2022/5/9 7:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePrivilege implements Serializable {
    private static final long serialVersionUID = -2530063862354202156L;
    private int id;
    private int roleId;
    private int privilegeId;

    public RolePrivilege(int roleId, int privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }
}
