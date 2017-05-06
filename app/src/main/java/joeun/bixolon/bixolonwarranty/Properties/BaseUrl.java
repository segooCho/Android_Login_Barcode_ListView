package joeun.bixolon.bixolonwarranty.Properties;

/**
 * Created by admin on 2017. 4. 25..
 */

public class BaseUrl {

    protected static final String BaseUrl = "http://192.168.2.27:3000/";
    protected static final String LoginUrl = BaseUrl + "employees";
    protected static final String BarcodeUrl = BaseUrl + "product";
    protected static final String ListViewUrl = BaseUrl + "warranty";

    public String getLoginUrl(){
        return this.LoginUrl;
    }
    public String getBarcodeUrl(){
        return this.BarcodeUrl;
    }
    public String getListViewUrl(){
        return this.ListViewUrl;
    }

}
