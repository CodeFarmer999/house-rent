package com.turing.service.impl;

import com.turing.dao.OwnerDao;
import com.turing.dao.impl.OwnerDaoImpl;
import com.turing.entity.User;
import com.turing.service.OwnerService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public class OwnerServiceImpl implements OwnerService {
    private OwnerDao dao = new OwnerDaoImpl();
    
    @Override
    public boolean addOwner(User user) {
        return dao.addOwner(user);
    }

    @Override
    public boolean deleteOwner(int id) {
        return dao.deleteOwner(id);
    }

    @Override
    public boolean updateOwner(User user) {
        return dao.updateOwner(user);
    }

    @Override
    public User getOwner(int id) {
        return dao.getOwner(id);
    }

    @Override
    public List<User> getOwners() {
        return dao.getOwners();
    }
}
