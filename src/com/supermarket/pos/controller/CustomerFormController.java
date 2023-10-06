package com.supermarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.CustomerBo;
import com.supermarket.pos.bo.custom.impl.CustomerBoImpl;
import com.supermarket.pos.dto.CustomerDto;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane context;
    public JFXTextField txtCustomerEmail;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerSalary;
    public JFXButton btnSaveUpdateCustomer;
    public TextField txtSearch;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;

    CustomerBo bo= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private String searchText="";

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        //In above code what we do is we map the column Ids to our variables in CustomerTm.
        // Otherwise the columns don't know which data to fetch from the observable list
        loadAllCustomers(searchText);

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            try {
                loadAllCustomers(searchText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void setData(CustomerTm newValue) {
        txtCustomerEmail.setEditable(false);
        btnSaveUpdateCustomer.setText("Update Customer");
        txtCustomerEmail.setText(newValue.getEmail());
        txtCustomerName.setText(newValue.getName());
        txtCustomerContact.setText(newValue.getContact());
        txtCustomerSalary.setText(String.valueOf(newValue.getSalary()));
    }

    private void loadAllCustomers(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
        int counter =1;
        for (CustomerDto dto: searchText.length()>0?bo.searchCustomers(searchText):bo.findAllCustomers()){
            //In the above line, we check if there's a text available in search. If searchtext is available, we load data from searchcustomers method.
            //if there's no search text, we load data from findallcustomers method.
            Button btn=new Button("Delete");
            CustomerTm tm= new CustomerTm(counter, dto.getEmail(),
                    dto.getName(), dto.getContact(), dto.getSalary(), btn);
            btn.setOnAction(e->{
                Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().equals(ButtonType.YES)){
                    try {
                        if(bo.deleteCustomer (dto.getEmail())){
                            new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                            loadAllCustomers(searchText);
                            clearFields();
                        }else{
                            new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                        };
                    } catch (ClassNotFoundException | SQLException ex) {
                        new Alert(Alert.AlertType.ERROR,e.toString()).show();
                    }
                }
            });
            observableList.add(tm);
            counter+=1;
        }
        tblCustomer.setItems(observableList);
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        clearFields();
        btnSaveUpdateCustomer.setText("Save Customer");
        txtCustomerEmail.setEditable(true);
    }

    public void btnManageLoyaltyCardsOnAction(ActionEvent actionEvent) {
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void btnSaveUpdateCustomerOnAction(ActionEvent actionEvent)  {
        try {
            Validator validator=new Validator();
            TextField[] textFieldsToValidate={
                    txtCustomerEmail,
                    txtCustomerName,
                    txtCustomerContact,
                    txtCustomerSalary
            };
            Pattern[] patternsToValidate={
                    validator.cusEmailPattern,
                    validator.cusNamePattern,
                    validator.cusContactPattern,
                    validator.cusSalaryPattern
            };
            if(validator.validate(textFieldsToValidate,patternsToValidate)){
                if(btnSaveUpdateCustomer.getText().equals("Save Customer")){
                    if(bo.saveCustomer(new CustomerDto(txtCustomerEmail.getText(),
                            txtCustomerName.getText(),txtCustomerContact.getText(),
                            Double.parseDouble(txtCustomerSalary.getText())))){
                        new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!").show();
                        btnSaveUpdateCustomer.setText("Update");
                        clearFields();
                        loadAllCustomers(searchText);
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                    }
                }else {
                    if(bo.updateCustomer(new CustomerDto( txtCustomerEmail.getText(),
                            txtCustomerName.getText(),txtCustomerContact.getText(),
                            Double.parseDouble(txtCustomerSalary.getText())))){
                        new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated!").show();
                        btnSaveUpdateCustomer.setText("Save Customer");
                        clearFields();
                        loadAllCustomers(searchText);
                        txtCustomerEmail.setEditable(true);
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                    }
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Please Enter Valid Data!").show();
            }
        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void clearFields() {
        txtCustomerEmail.clear();
        txtCustomerName.clear();
        txtCustomerContact.clear();
        txtCustomerSalary.clear();
    }
    private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.centerOnScreen();
    }
}
