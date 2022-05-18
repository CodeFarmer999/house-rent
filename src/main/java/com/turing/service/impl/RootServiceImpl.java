package com.turing.service.impl;

import com.turing.dao.RootDao;
import com.turing.dao.impl.RootDaoImpl;
import com.turing.entity.User;
import com.turing.service.RootService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/10 9:56
 **/
public class RootServiceImpl implements RootService {
    private RootDao dao = new RootDaoImpl();

    @Override
    public List<User> selectAdmin() {
        return dao.selectAdmin();
    }

    @Override
    public List<User> selectRoot() {
        return dao.selectRoot();
    }

    @Override
    public boolean addAdmin(int id) {
        return dao.addAdmin(id);
    }

    @Override
    public boolean deleteAdmin(int id) {
        return dao.deleteAdmin(id);
    }

    @Override
    public boolean updateAdmin(User user) {
        return dao.updateAdmin(user);
    }
}
