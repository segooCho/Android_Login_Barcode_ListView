package joeun.bixolon.bixolonwarranty.ListView;

import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2017. 5. 4..
 */


public class ListViewEventModel {

    private Drawable iconDrawable ;
    private String barcode ;
    private String productName ;
    private String desc ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setBarcode(String _barcode) {
        barcode = _barcode ;
    }
    public void setProductName(String _productName) {
        productName = _productName ;
    }
    public void setDesc(String _desc) {
        desc = _desc ;
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
    public String getDesc() {
        return this.desc ;
    }
}
