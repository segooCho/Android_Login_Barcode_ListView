package joeun.bixolon.bixolonwarranty.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2017. 5. 4..
 */


public class ListViewEventModel {

    private Drawable iconDrawable;
    private String serialNo;
    private String model;
    private String buyerCode;
    private String warrantyCode;
    private String goingOutDate;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setSerialNo(String _serialNo) {
        serialNo = _serialNo ;
    }
    public void setModel(String _model) {
        model = _model ;
    }
    public void setBuyerCode(String _buyerCode) {
        buyerCode = _buyerCode ;
    }
    public void setWarrantyCode(String _warrantyCode) {
        warrantyCode = _warrantyCode ;
    }
    public void setGoingOutDate(String _goingOutDate) {
        goingOutDate = _goingOutDate ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getSerialNo() {
        return this.serialNo ;
    }
    public String getModel() {
        return this.model ;
    }
    public String getBuyerCode() {
        return this.buyerCode ;
    }
    public String getWarrantyCode() {
        return this.warrantyCode ;
    }
    public String getGoingOutDate() {
        return this.goingOutDate ;
    }
}
