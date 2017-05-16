package joeun.bixolon.bixolonwarranty.Properties;

/**
 * Created by admin on 2017. 4. 25..
 */

public class BaseUrl {

    //Server
    protected static final String baseUrl = "http://192.168.2.27:3000/";
    //로그인
    protected static final String loginUrl = baseUrl + "login";
    //공통 spinner 처리
    protected static final String commonSpinnerUrl = baseUrl + "commonSpinner";
    //barcode :: barcode scan
    protected static final String barcodeUrl = baseUrl + "barcode";
    //barcode :: warrantyCode & salesDate 조합 유효기간
    protected static final String expiryDateUrl = baseUrl + "expiryDate";
    //listview :: listview 조회
    protected static final String listViewUrl = baseUrl + "warranty";

    /*
    public String getBaseUrl(){
        return this.baseUrl;
    }
    */
    public String getLoginUrl(){
        return this.loginUrl;
    }
    public String getCommonSpinner(){
        return this.commonSpinnerUrl;
    }
    public String getBarcodeUrl(){
        return this.barcodeUrl;
    }
    public String getExpiryDateUrl(){
        return this.expiryDateUrl;
    }
    public String getListViewUrl(){
        return this.listViewUrl;
    }

}
