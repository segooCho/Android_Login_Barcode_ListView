package joeun.bixolon.bixolonwarranty.Model;

/**
 * Created by admin on 2017. 5. 4..
 */


public class LoginEventModel {

    private String id;
    private String buyer;
    private String serviceCenter;

    public void setId(String _id) {
        id = _id ;
    }
    public void setBuyer(String _buyer) {
        buyer = _buyer ;
    }
    public void setServiceCenter(String _serviceCenter) {
        serviceCenter = _serviceCenter ;
    }

    public String getId() {
        return this.id ;
    }
    public String getBuyer() {
        return this.buyer ;
    }
    public String getServiceCenter() {
        return this.serviceCenter ;
    }

}
