package joeun.bixolon.bixolonwarranty.Properties;

/**
 * Created by admin on 2017. 4. 25..
 */

public class BaseUrl {

    protected static final String baseUrl = "http://192.168.2.27:3000/";
    protected static final String loginUrl = baseUrl + "employees";
    protected static final String barcodeUrl = baseUrl + "product";
    protected static final String listViewUrl = baseUrl + "warranty";

    public String getBaseUrl(){
        return this.baseUrl;
    }
    public String getLoginUrl(){
        return this.loginUrl;
    }
    public String getBarcodeUrl(){
        return this.barcodeUrl;
    }
    public String getListViewUrl(){
        return this.listViewUrl;
    }

}
