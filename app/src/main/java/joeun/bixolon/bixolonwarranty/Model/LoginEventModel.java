package joeun.bixolon.bixolonwarranty.Model;

/**
 * Created by admin on 2017. 5. 4..
 */


public class LoginEventModel {

    private String userId;
    private String userName;
    private String buyer;
    private String serviceCenter;

    public void setUserId(String _userId) {
        userId = _userId ;
    }

    public void setUserName(String _userName) {
        userName = _userName ;
    }

    public void setBuyer(String _buyer) {
        buyer = _buyer ;
    }

    public void setServiceCenter(String _serviceCenter) {
        serviceCenter = _serviceCenter ;
    }

    public String getUserId() {
        return this.userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getBuyer() {
        return this.buyer;
    }
    public String getServiceCenter() {
        return this.serviceCenter;
    }

}
