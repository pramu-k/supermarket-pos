package com.supermarket.pos.dao.custom;

import com.supermarket.pos.dao.CrudDao;
import com.supermarket.pos.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer,String> {
     List<Customer> searchCustomers(String searchText) throws SQLException, ClassNotFoundException;
}
