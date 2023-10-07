package com.supermarket.pos.controller;

import com.supermarket.pos.dao.DaoFactory;
import com.supermarket.pos.dao.custom.ItemDetailDao;
import com.supermarket.pos.dao.custom.ProductDetailDao;
import com.supermarket.pos.entity.ItemDetail;
import com.supermarket.pos.enums.DaoType;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;

public class IncomeReportFormController {
    public Label lblTotalRevenue;
    public Label lblCostOfGoods;
    public Label lblGrossProfit;
    public Label lblOpExpenses;
    public Label lblNetIncome;
    private ItemDetailDao itemDetailDao= DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    private ProductDetailDao productDetailDao= DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAILS);
    private List<ItemDetail> itemDetailList;
    public void initialize() throws SQLException, ClassNotFoundException {
        profitCalc();
    }

    {
        try {
            itemDetailList = itemDetailDao.findAll();
        } catch (SQLException |ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private double costOfGoodsCalc() throws SQLException, ClassNotFoundException {
        double totalCost=0;
        for (ItemDetail item:itemDetailList) {
            double buyingPrice=productDetailDao.findBuyingPriceByBatchCode(item.getDetailCode());
            totalCost+=(buyingPrice*item.getQty());
        }
        lblCostOfGoods.setText("LKR "+totalCost);
        return totalCost;
    }
    private double revenueCalc(){
        double totalRevenue=0;
        for (ItemDetail item:itemDetailList) {
            totalRevenue+=item.getAmount();
        }
        lblTotalRevenue.setText("LKR "+totalRevenue);
        return totalRevenue;
    }
    private void profitCalc() throws SQLException, ClassNotFoundException {
        double grossProfit=revenueCalc()-costOfGoodsCalc();
        lblGrossProfit.setText("LKR "+grossProfit);
        lblNetIncome.setText("LKR "+grossProfit);
    }

}
