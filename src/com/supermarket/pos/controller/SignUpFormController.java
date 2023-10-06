package com.supermarket.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.UserBo;
import com.supermarket.pos.bo.custom.impl.UserBoImpl;
import com.supermarket.pos.dto.UserDto;
import com.supermarket.pos.enums.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {
    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    UserBo bo= BoFactory.getInstance().getBo(BoType.USER);

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        try {
            if(bo.saveUser(new UserDto( txtEmail.getText(),txtPassword.getText()))){
                new Alert(Alert.AlertType.CONFIRMATION,"User Saved Successfully!").show();
                clearFields();
                try {
                    setUi("LoginForm");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"User not Saved. Try Again!").show();
            }
        }catch (ClassNotFoundException| SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtPassword.clear();
    }

    public void btnAlreadyHaveAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }
    private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.centerOnScreen();
    }
}
