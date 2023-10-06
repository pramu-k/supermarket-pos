package com.supermarket.pos.bo.custom.impl;

import com.supermarket.pos.bo.custom.CustomerBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.CustomerDao;
import com.supermarket.pos.dto.CustomerDto;
import com.supermarket.pos.entity.Customer;
import com.supermarket.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.save(
                new Customer(dto.getEmail(), dto.getName(), dto.getContact(), dto.getSalary())
        );
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(dto.getEmail(), dto.getName(), dto.getContact(), dto.getSalary())
        );
    }

    @Override
    public boolean deleteCustomer(String email) throws SQLException, ClassNotFoundException {
        return customerDao.delete(email);
    }

    @Override
    public CustomerDto findCustomer(String email) throws SQLException, ClassNotFoundException {
        Customer customer = customerDao.find(email);
        if (customer!=null) {
            return new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            );
        }
        return null;
    }

    @Override
    public List<CustomerDto> findAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDto> dtos = new ArrayList<>();

        for (Customer c:customerDao.findAll()
        ) {
            dtos.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return dtos;
    }
    public List<CustomerDto> searchCustomers(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer c:customerDao.searchCustomers(searchText)
        ) {
            dtos.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return dtos;
    }
}
