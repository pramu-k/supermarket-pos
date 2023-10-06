package com.supermarket.pos.dao.custom.impl;

import com.supermarket.pos.dao.CrudUtil;
import com.supermarket.pos.dao.custom.UserDao;
import com.supermarket.pos.entity.User;
import com.supermarket.pos.util.PasswordManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("INSERT INTO user VALUES (?,?)",user.getEmail(),PasswordManager.encryptPassword(user.getPassword()));
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("UPDATE user SET password=? WHERE email=? ",PasswordManager.encryptPassword(user.getPassword()),user.getEmail());
    }

    @Override
    public boolean delete(String email) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("DELETE FROM user WHERE email=?",email);
    }

    @Override
    public User find(String email) throws SQLException, ClassNotFoundException {

        ResultSet set =CrudUtil.execute("SELECT * FROM user WHERE email=?",email);
        if(set.next()){
            return new User(set.getString(1),
                    set.getString(2));
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException, ClassNotFoundException {
        ResultSet set =CrudUtil.execute("SELECT * FROM user ");

        List<User> userList=new ArrayList<>();
        while(set.next()){
            userList.add(new User(set.getString(1),set.getString(2)));
        }
        return userList;
    }


}
