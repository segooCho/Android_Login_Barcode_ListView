package joeun.bixolon.bixolonwarranty.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2017. 5. 4..
 */


public class ListViewEventModel {

    private Drawable iconDrawable;
    private String barcode;
    private String productName;
    private String warrantyType;
    private String warrantyDate;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setBarcode(String _barcode) {
        barcode = _barcode ;
    }
    public void setProductName(String _productName) {
        productName = _productName ;
    }
    public void setWarrantyType(String _warrantyType) {
        warrantyType = _warrantyType ;
    }
    public void setWarrantyDate(String _warrantyDate) {
        warrantyDate = _warrantyDate ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getBarcode() {
        return this.barcode ;
    }
    public String getProductName() {
        return this.productName ;
    }
    public String getWarrantyType() {
        return this.warrantyType ;
    }
    public String getWarrantyDate() {
        return this.warrantyDate ;
    }
}
