package com.supermarket.pos.controller;


import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.OrderBo;
import com.supermarket.pos.dto.OrderDto;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.view.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class OrdersFormController {
    public TableView tblOrderDetails;
    public TableColumn colOrderId;
    public TableColumn colOrderDate;
    public TableColumn colDiscount;
    public TableColumn colTotalCost;
    public TableColumn colCustomerEmail;
    public TableColumn colUserEmail;

    OrderBo orderBo= BoFactory.getInstance().getBo(BoType.ORDER);
    public void initialize() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("totalDiscount"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        loadTableData();
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        List<OrderDto> orderDtos= orderBo.getAllOrders();
        ObservableList<OrderTm> observableOrderList= FXCollections.observableArrayList();
        for (OrderDto dto: orderDtos) {
            observableOrderList.add(new OrderTm(
                    dto.getOrderCode(),
                    dto.getIssuedDate(),
                    dto.getDiscount(),
                    dto.getTotalCost(),
                    dto.getCustomerEmail(),
                    dto.getOperatorEmail()));
        }
        tblOrderDetails.setItems(observableOrderList);
    }

}
