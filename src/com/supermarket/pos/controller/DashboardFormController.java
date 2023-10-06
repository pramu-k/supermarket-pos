package com.supermarket.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane context;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void btnProductOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProductMainForm");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceOrderForm");
    }

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Stage newFormStage = new Stage();
        newFormStage.setTitle("View Orders");
        newFormStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrdersForm.fxml"))));
        newFormStage.show();
    }

    public void btnIncomeReportOnAction(ActionEvent actionEvent) {
    }
    private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.setTitle(url);
        stage.centerOnScreen();
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
}
