package com.supermarket.pos.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.supermarket.pos.bo.BoFactory;
import com.supermarket.pos.bo.custom.ProductDetailsBo;
import com.supermarket.pos.dto.ProductDetailDto;
import com.supermarket.pos.enums.BoType;
import com.supermarket.pos.util.QrDataGenerator;
import com.supermarket.pos.view.tm.ProductDetailTm;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class NewBatchFormController {

    public ImageView barCodeImage;
    public AnchorPane context;
    public TextField txtProductCode;
    public TextArea txtProductDesc;
    public TextField txtQty;
    public TextField txtBuyingPrice;
    public TextField txtSellingPrice;
    public TextField txtShowPrice;
    public JFXRadioButton rBtnYes;
    public JFXRadioButton rBtnNo;
    public JFXButton btnSaveBatch;
    String uniqueData=null;
    BufferedImage bufferedImage = null;
    Stage stage=null;
    private ProductDetailsBo productDetailsBo= BoFactory.getInstance().getBo(BoType.PRODUCT_DETAILS);


    public void initialize() throws WriterException {
        setQRCode();
    }

    private void setQRCode() throws WriterException {
        uniqueData = QrDataGenerator.generate(25);
        //----------------------Gen QR
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        bufferedImage =
                MatrixToImageWriter.toBufferedImage(
                        qrCodeWriter.encode(
                                uniqueData,
                                BarcodeFormat.QR_CODE,
                                160, 160
                        )
                );
        //----------------------Gen QR
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        barCodeImage.setImage(image);
    }
    public void setDetails(int code, String description, Stage stage, boolean state, ProductDetailTm tm){
        this.stage=stage;
        if(state){
            //load
            try {
                ProductDetailDto productDetails = productDetailsBo.findProductDetails(tm.getCode());
                if(productDetails!=null){
                    //load data
                    btnSaveBatch.setDisable(true);
                    txtQty.setText(String.valueOf(productDetails.getQtyOnHand()));
                    txtBuyingPrice.setText(String.valueOf(productDetails.getBuyingPrice()));
                    txtSellingPrice.setText(String.valueOf(productDetails.getSellingPrice()));
                    txtShowPrice.setText(String.valueOf(productDetails.getShowPrice()));
                    rBtnYes.setSelected(productDetails.isDiscountAvailability());

                    byte[] imageData=Base64.decodeBase64(productDetails.getBarcode());
                    barCodeImage.setImage(new Image(new ByteArrayInputStream(imageData)));

                }else {
                    stage.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        txtProductCode.setText(String.valueOf(code));
        txtProductDesc.setText(description);



    }

    public void btnSaveBatchOnAction(ActionEvent actionEvent) throws IOException {
        //baos=byte array output stream
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(bufferedImage,"png",baos);
        byte[] arr = baos.toByteArray();
        ProductDetailDto dto = new ProductDetailDto(
                uniqueData,
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtSellingPrice.getText()),
                Double.parseDouble(txtShowPrice.getText()),
                Double.parseDouble(txtBuyingPrice.getText()),
                Base64.encodeBase64String(arr),
                Integer.parseInt(txtProductCode.getText()),
                rBtnYes.isSelected()?true:false);
        try {
            if(productDetailsBo.saveProductDetails(dto)){
                new Alert(Alert.AlertType.CONFIRMATION,"Batch Saved!").show();
               Thread.sleep(3000);
               this.stage.close();
            }else {
                new Alert(Alert.AlertType.WARNING,"Please Try Again!").show();
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackToProductMainOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();

    }
    private void setUi (String url) throws IOException {
        Stage stage =(Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
        stage.setTitle(url);
        stage.centerOnScreen();
    }
}
