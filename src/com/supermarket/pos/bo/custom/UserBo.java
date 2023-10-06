package com.supermarket.pos.bo.custom;

import com.supermarket.pos.bo.SuperBo;
import com.supermarket.pos.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException;
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException;
    public UserDto findUser(String email) throws SQLException, ClassNotFoundException;
    public List<UserDto> findAllUsers() throws SQLException, ClassNotFoundException;

}
