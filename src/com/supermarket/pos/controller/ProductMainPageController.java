package com.supermarket.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.ProductBo;
import com.supermarket.pos.bo.custom.ProductDetailsBo;
import com.supermarket.pos.dto.ProductDetailDto;
import com.supermarket.pos.dto.ProductDto;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.view.tm.ProductDetailTm;
import com.supermarket.pos.view.tm.ProductTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class ProductMainPageController {
    public AnchorPane context;
    public TextArea txtProductDescription;
    public TextField txtSelectedProductDescription;
    public TableView<ProductDetailTm> tblProductInventory;
    public JFXTextField txtProductCode;
    public TableView<ProductTm> tblProducts;
    public TableColumn colProductId;
    public TableColumn colProductDescription;
    public TableColumn colShowMore;
    public TableColumn colDeleteProduct;
    public TextField txtSearchProducts;
    public TextField txtSelectedProductId;
    public JFXButton btnNewBatch;
    public TableColumn colPDId;
    public TableColumn colPDQty;
    public TableColumn colPDSellingPrice;
    public TableColumn colPDBuyingPrice;
    public TableColumn colPDDiscAvailability;
    public TableColumn colPDShowPrice;
    public TableColumn colPDDelete;
    ProductBo bo= BoFactory.getInstance().getBo(BoType.PRODUCT);
    ProductDetailsBo productDetailsBo=BoFactory.getInstance().getBo(BoType.PRODUCT_DETAILS);
    private String searchText="";

    public void initialize() throws SQLException, ClassNotFoundException {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId")); //names are taken from Tm class
        colProductDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colShowMore.setCellValueFactory(new PropertyValueFactory<>("showMore"));
        colDeleteProduct.setCellValueFactory(new PropertyValueFactory<>("btnDeleteProduct"));

        colPDId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colPDQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPDSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colPDBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colPDDiscAvailability.setCellValueFactory(new PropertyValueFactory<>("discountAvailability"));
        colPDShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colPDDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        //load new product id
        loadProductId();
        loadAllProducts(searchText);

        tblProducts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setData(newValue);
        });
        tblProductInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadExternalUi(true,newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setData(ProductTm newValue) {
        txtSelectedProductId.setText(String.valueOf(newValue.getProductId()));
        txtSelectedProductDescription.setText(newValue.getProductDescription());
        btnNewBatch.setDisable(false);
        try {
            loadBatchData(newValue.getProductId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllProducts(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<ProductTm> tms= FXCollections.observableArrayList();
        for (ProductDto dto: bo.findAllProducts()) {
            Button showMore=new Button("Show More");
            Button deleteBtn=new Button("Delete");
            ProductTm productTm=new ProductTm(dto.getCode(), dto.getDescription(),showMore,deleteBtn );
            deleteBtn.setOnAction(event -> {
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?",ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType=alert.showAndWait();
                if(buttonType.get().equals(ButtonType.YES)) {
                    try {
                        if (bo.deleteProduct(dto.getCode())) {
                            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                            loadAllProducts(searchText);
                            loadProductId();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    }catch(SQLIntegrityConstraintViolationException e){
                        new Alert(Alert.AlertType.WARNING, "Cannot delete this field. Please Try again later!").show();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                }
            });
            tms.add(productTm);
        }
        tblProducts.setItems(tms);
    }

    private void loadProductId() {
        try {
            txtProductCode.setText(String.valueOf(bo.getLastProductId()+1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void btnSaveProductOnAction(ActionEvent actionEvent)  {
        try{
            Validator validator=new Validator();
            TextArea[] textFieldsToValidate={
                    txtProductDescription
            };
            Pattern[] patternsToValidate={
                    validator.prodDescPattern
            };
            if(validator.validate(textFieldsToValidate,patternsToValidate)){
                if(bo.saveProduct(new ProductDto( Integer.parseInt(txtProductCode.getText())
                        ,txtProductDescription.getText()))){
                    new Alert(Alert.AlertType.CONFIRMATION,"Product Saved!").show();
                    loadAllProducts(searchText);
                    clearFields();
                    loadProductId();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Please Enter Valid Data!").show();
            }


        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnNewBatchOnAction(ActionEvent actionEvent) throws IOException {
       loadExternalUi(false,null);


    }
    private void loadBatchData(int code) throws SQLException, ClassNotFoundException {
        ObservableList<ProductDetailTm> observableList=FXCollections.observableArrayList();
        for (ProductDetailDto d: productDetailsBo.findAllProductDetails(code)) {
            Button btn = new Button("Delete");
            ProductDetailTm tm=new ProductDetailTm(
                    d.getCode(),
                    d.getQtyOnHand(),
                    d.getSellingPrice(),
                    d.getBuyingPrice(),
                    d.isDiscountAvailability(),
                    d.getShowPrice(),
                    btn
                    );
            observableList.add(tm);
            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        if (productDetailsBo.deleteProductDetails(d.getCode())) {
                            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                            loadBatchData(code);
                            clearFields();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

        }});
        }
        tblProductInventory.setItems(observableList);
    }
   private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.centerOnScreen();
    }
    private void clearFields(){
        txtProductCode.clear();
        txtProductDescription.clear();
    }
    private void loadExternalUi(boolean state,ProductDetailTm tm) throws IOException {
        if(!txtSelectedProductId.getText().isEmpty()){
            Stage stage = new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("../view/NewBatchForm.fxml"));
            Parent parent=fxmlLoader.load();
            NewBatchFormController controller= fxmlLoader.getController();
            controller.setDetails(Integer.parseInt(txtSelectedProductId.getText()),
                    txtSelectedProductDescription.getText(),stage,state,tm);

            stage.setScene(new Scene(parent));
            stage.show();
            stage.centerOnScreen();
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Select A Product!").show();
        }
    }
}
