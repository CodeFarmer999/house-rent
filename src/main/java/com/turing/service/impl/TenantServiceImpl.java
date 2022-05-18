package com.turing.service.impl;

import com.turing.dao.TenantDao;
import com.turing.dao.impl.TenantDaoImpl;
import com.turing.entity.User;
import com.turing.service.TenantService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public class TenantServiceImpl implements TenantService {
    private TenantDao dao = new TenantDaoImpl();
    
    @Override
    public boolean addTenant(User user) {
        return dao.addTenant(user);
    }

    @Override
    public boolean deleteTenant(int id) {
        return dao.deleteTenant(id);
    }

    @Override
    public boolean updateTenant(User user) {
        return dao.updateTenant(user);
    }

    @Override
    public User getTenant(int id) {
        return dao.getTenant(id);
    }

    @Override
    public List<User> getTenants() {
        return dao.getTenants();
    }
}
