package com.turing.service.impl;

import com.turing.dao.MiddlemanDao;
import com.turing.dao.impl.MiddlemanDaoImpl;
import com.turing.entity.User;
import com.turing.service.MiddlemanService;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/11 9:10
 **/
public class MiddlemanServiceImpl implements MiddlemanService {
    private MiddlemanDao dao = new MiddlemanDaoImpl();
    
    @Override
    public boolean addMiddleman(User user) {
        return dao.addMiddleman(user);
    }

    @Override
    public boolean deleteMiddleman(int id) {
        return dao.deleteMiddleman(id);
    }

    @Override
    public boolean updateMiddleman(User user) {
        return dao.updateMiddleman(user);
    }

    @Override
    public User getMiddleman(int id) {
        return dao.getMiddleman(id);
    }

    @Override
    public List<User> getMiddlemans() {
        return dao.getMiddlemans();
    }
}
