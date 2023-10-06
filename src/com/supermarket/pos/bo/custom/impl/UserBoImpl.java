package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.UserBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.UserDao;
import com.supermarket.pos.dto.CustomerDto;
import com.supermarket.pos.dto.UserDto;
import com.supermarket.pos.entity.Customer;
import com.supermarket.pos.entity.User;
import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao=  DaoFactory.getInstance().getDao(DaoType.USER);
    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.save(new User(dto.getEmail(), dto.getPassword()));
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDao.update(new User(dto.getEmail(), dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        return userDao.delete(email);
    }

    @Override
    public UserDto findUser(String email) throws SQLException, ClassNotFoundException {
        User user = userDao.find(email);
        if (user!=null) {
            return new UserDto(
                    user.getEmail(),
                    user.getPassword());
        }
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDto> dtos = new ArrayList<>();

        for (User c:userDao.findAll()) {
            dtos.add(new UserDto(c.getEmail(), c.getPassword()));
        }
        return dtos;
    }
}
