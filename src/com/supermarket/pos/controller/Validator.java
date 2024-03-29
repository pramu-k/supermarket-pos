package com.supermarket.pos.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class Validator {
    final String CUS_EMAIL_REG="^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$";
    final String CUS_NAME_REG="^[a-zA-Z\\s]{2,}$";
    final String CUS_CONTACT_REG="^0[1-9]{1}[0-9]{8}$";
    final String CUS_SALARY_REG="^[1-9]\\d*(\\.\\d{1,2})?$";
    final String PROD_DESC_REG="^[a-zA-Z0-9].*$";
    final String PASSWORD_REG="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";



    public Pattern emailPattern=Pattern.compile(CUS_EMAIL_REG);
    public Pattern cusNamePattern=Pattern.compile(CUS_NAME_REG);
    public Pattern cusContactPattern=Pattern.compile(CUS_CONTACT_REG);
    public Pattern cusSalaryPattern=Pattern.compile(CUS_SALARY_REG);
    public Pattern prodDescPattern=Pattern.compile(PROD_DESC_REG);
    public Pattern passwordPattern=Pattern.compile(PASSWORD_REG);

    public boolean validate(TextInputControl[] textFields, Pattern[] patterns){
        boolean output =true;
        for (int i = 0; i < textFields.length; i++) {
            if(!validateFields(textFields[i].getText(),patterns[i])){
                textFields[i].setStyle("-fx-border-color: red");
                output=false;
            }else {
                textFields[i].setStyle("-fx-border-color: green");
            }
        }
        return output;

    }
    private boolean validateFields(String name,Pattern pattern){
        if(pattern.matcher(name).matches()){
            return true;
        }else {
            return false;
        }
    }

}
