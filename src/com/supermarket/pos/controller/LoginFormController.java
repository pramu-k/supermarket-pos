package com.supermarket.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.CustomerBo;
import com.supermarket.pos.bo.custom.UserBo;
import com.supermarket.pos.bo.custom.impl.UserBoImpl;
import com.supermarket.pos.dto.UserDto;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.util.PasswordManager;
import com.supermarket.pos.util.UserSessionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;

    UserBo bo= BoFactory.getInstance().getBo(BoType.USER);

    public void btnSignInOnAction(ActionEvent actionEvent) {
        try {
            UserDto ud = bo.findUser(txtEmail.getText());
            if(ud!=null){
                if(PasswordManager.checkPassword(txtPassword.getText(),ud.getPassword())){
                    UserSessionData.email=txtEmail.getText();
                    setUi("DashboardForm");
                }else {
                    new Alert(Alert.AlertType.WARNING,"Check Password and Try Again!").show();
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"User Not Found!").show();
            }
        }catch (ClassNotFoundException | SQLException | IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignUpForm");
    }
    private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.centerOnScreen();
    }
}
