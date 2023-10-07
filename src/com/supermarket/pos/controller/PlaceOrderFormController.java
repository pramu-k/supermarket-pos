package com.supermarket.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXTextField;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.CustomerBo;
import com.supermarket.pos.bo.custom.LoyaltyCardBo;
import com.supermarket.pos.bo.custom.OrderDetailBo;
import com.supermarket.pos.bo.custom.ProductDetailsBo;
import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.OrderDao;
import com.supermarket.pos.dto.*;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.enums.CardType;
import com.supermarket.pos.enums.DaoType;
import com.supermarket.pos.util.QrDataGenerator;
import com.supermarket.pos.util.UserSessionData;
import com.supermarket.pos.view.tm.CartTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PlaceOrderFormController {
    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public Label lblLoyaltyType;
    public Hyperlink urlNewLoyalty;
    public JFXTextField txtBarcode;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtDiscount;
    public JFXTextField txtShowPrice;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtBuyingPrice;
    public Label lblDiscAvail;
    public TableView tblCart;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colSellPrice;
    public TableColumn colShowPrice;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public TableColumn colOperation;
    public TableColumn colDiscount;
    public Label lblGrandTotal;

    CustomerBo customerBo= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    OrderDao orderDao= DaoFactory.getInstance().getDao(DaoType.ORDER);
    private ProductDetailsBo productDetailsBo= BoFactory.getInstance().getBo(BoType.PRODUCT_DETAILS);
    private OrderDetailBo orderDetailBo= BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private LoyaltyCardBo loyaltyCardBo= BoFactory.getInstance().getBo(BoType.LOYALTY_CARD);

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colShowPrice.setCellValueFactory(new PropertyValueFactory<>("showPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
    }
    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm",false);
    }

    public void btnAddNewProductOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProductMainForm",true);
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm",true);
    }
    private void setUi (String url,boolean state) throws IOException {
        Stage stage =null;
        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")));
        if(state){
            stage=new Stage();
            stage.setScene(scene);
            stage.show();
        }else {
            stage=(Stage) context.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }


    }

    public void newLoyaltyOnAction(ActionEvent actionEvent) {
        try {
            double salary=Double.parseDouble(txtSalary.getText());
            CardType cardType=null;
            if(salary>=100000){
                cardType=CardType.PLATINUM;
            } else if (salary>=50000) {
                cardType=CardType.GOLD;
            }else {
                cardType=CardType.SILVER;
            }
            String uniqueData = QrDataGenerator.generate(25);
            //----------------------Gen QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BufferedImage bufferedImage =
                    MatrixToImageWriter.toBufferedImage(
                            qrCodeWriter.encode(
                                    uniqueData,
                                    BarcodeFormat.QR_CODE,
                                    160, 160
                            )
                    );
            ByteArrayOutputStream baos= new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage,"png",baos);
            byte[] arr = baos.toByteArray();
            if(urlNewLoyalty.getText().equals("+ New Loyalty")){
                boolean isLoyaltyCardAssigned = loyaltyCardBo.saveLoyaltyCard(new LoyaltyCardDto(
                        new Random().nextInt(10001),
                        cardType,
                        Base64.encodeBase64String(arr),
                        txtEmail.getText()));
                if(isLoyaltyCardAssigned){
                    new Alert(Alert.AlertType.INFORMATION,"Loyalty Customer Successfully Saved!").show();
                    urlNewLoyalty.setText(("Show Loyalty Card Info"));
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try Again Shortly!").show();
                }
            }else {
                //view data
            }

        }catch (SQLException|ClassNotFoundException e){
            new Alert(Alert.AlertType.WARNING,"Try Again Shortly!").show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        try {
            CustomerDto customer = customerBo.findCustomer(txtEmail.getText());
            if(customer!=null){
                txtName.setText(customer.getName());
                txtContact.setText(customer.getContact());
                txtSalary.setText(String.valueOf(customer.getSalary()));

                fetchLoyaltyCardData(txtEmail.getText());
            }else {
                new Alert(Alert.AlertType.WARNING,"Customer Not Found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING,"Customer Not Found!").show();
            throw new RuntimeException(e);
        }
    }

    private void fetchLoyaltyCardData(String email) {
        urlNewLoyalty.setText("+ New Loyalty");
        urlNewLoyalty.setVisible(true);
    }

    public void loadProductOnAction(ActionEvent actionEvent){
        try {
            ProductDetailJoinDto p = productDetailsBo.findProductJoinDetails(txtBarcode.getText());
            if(p!=null){
                txtDescription.setText(p.getDescription());
                txtQtyOnHand.setText(String.valueOf(p.getProductDetailDto().getQtyOnHand()));
                txtDiscount.setText(String.valueOf(0));
                txtShowPrice.setText(String.valueOf(p.getProductDetailDto().getShowPrice()));
                txtSellingPrice.setText(String.valueOf(p.getProductDetailDto().getSellingPrice()));
                txtBuyingPrice.setText(String.valueOf(p.getProductDetailDto().getBuyingPrice()));
                txtQty.requestFocus();
            }else {
                new Alert(Alert.AlertType.WARNING,"Can't Find the Product!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    ObservableList<CartTm> tms= FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) {

        int qty=Integer.parseInt(txtQty.getText());
        double sellingPrice=Double.parseDouble(txtSellingPrice.getText());
        double totalCost=qty*sellingPrice;
        double discount=Double.parseDouble(txtShowPrice.getText())-sellingPrice;

        CartTm selectedCartTm=isExist(txtBarcode.getText());

        if(selectedCartTm!=null){
            //update
            selectedCartTm.setQty(qty+selectedCartTm.getQty());
            selectedCartTm.setTotalCost(totalCost+selectedCartTm.getTotalCost());
            tblCart.refresh();
            clear();
        }else {
            //create new
            Button btn= new Button("Remove");
            CartTm tm=new CartTm(
                    txtBarcode.getText(),
                    txtDescription.getText(),
                    sellingPrice,
                    Double.parseDouble(txtShowPrice.getText()),
                    qty,
                    totalCost,
                    btn,
                    discount);
            tms.add(tm);
            tblCart.setItems(tms);
            clear();

            btn.setOnAction(event -> {
                tms.remove(tm);
                tblCart.refresh();
                setTotal();
            });
        }
        setTotal();



    }
    private CartTm isExist(String code){
        for (CartTm tm:tms) {
            if(tm.getCode().equals(code)){
                return tm;
            }
        }
        return null;
    }
    private void setTotal(){
        double grandTotal=0;
        for (CartTm tm:tms) {
            grandTotal+=tm.getTotalCost();
        }
        lblGrandTotal.setText(grandTotal+"/=");
    }
    private void clear(){
        txtBarcode.clear();
        txtDescription.clear();
        txtQty.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtShowPrice.clear();
        txtSellingPrice.clear();
        txtBuyingPrice.clear();
    }

    public void btnCompleteOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!tms.isEmpty()){
            ArrayList<ItemDetailDto> dtoList=new ArrayList<>();
            double discount=0;
            for (CartTm tm:tms) {
                dtoList.add(new ItemDetailDto(
                        tm.getCode(),
                        tm.getQty(),
                        tm.getDiscount(),
                        tm.getTotalCost()));
                discount+=tm.getDiscount();
            }
            OrderDetailDto orderDetailDto=new OrderDetailDto(
                    generateOrderId(),
                    new Date(),
                    Double.parseDouble(lblGrandTotal.getText().split("/=")[0]),
                    txtEmail.getText(),
                    discount,
                    UserSessionData.email,
                    dtoList);
            try {
                if(orderDetailBo.makeOrder(orderDetailDto)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Order Saved!").show();
                    clearFields();
                    tms.clear();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try Again!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Please add products before continue!").show();
        }

    }

    private void clearFields() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
        lblGrandTotal.setText("00/=");
    }
    private int generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.getLastProductId()+1;
    }

}
