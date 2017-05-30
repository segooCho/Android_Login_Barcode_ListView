package joeun.bixolon.bixolonwarranty.Properties;

/**
 * Created by admin on 2017. 4. 25..
 */

public class BaseUrl {

    //Server
    //protected static final String baseUrl = "http://106.245.251.59:3000/";            //조은 서버
    //protected static final String baseUrl = "http://192.168.2.33:3000/";              //개발 서버
    protected static final String baseUrl = "http://220.120.107.3:8895/";               //빅솔론 서버

    //로그인
    protected static final String loginUrl = baseUrl + "login";
    //공통 spinner 처리
    protected static final String commonSpinnerUrl = baseUrl + "commonSpinner";
    //barcode :: barcode scan
    protected static final String barcodeUrl = baseUrl + "barcode";
    //barcode :: warrantyCode & salesDate 조합 유효기간
    protected static final String expiryDateUrl = baseUrl + "expiryDate";
    //barcode :: barcode Save
    protected static final String barcodeSaveUrl = baseUrl + "barcodeSave";
    //listview :: listview 조회
    protected static final String workListUrl = baseUrl + "workList";

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
    public String getBarcodSaveUrl(){
        return this.barcodeSaveUrl;
    }
    public String getExpiryDateUrl(){
        return this.expiryDateUrl;
    }
    public String getWorkListUrl(){
        return this.workListUrl;
    }

}
