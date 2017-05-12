package joeun.bixolon.bixolonwarranty.Model;

/**
 * Created by admin on 2017. 5. 4..
 */


public class LoginEventModel {

    private String id;
    private String corporationInfo;
    private String serviceCenter;

    public void setId(String _id) {
        id = _id ;
    }
    public void setCorporationInfo(String _corporationInfo) {
        corporationInfo = _corporationInfo ;
    }
    public void setServiceCenter(String _serviceCenter) {
        serviceCenter = _serviceCenter ;
    }

    public String getId() {
        return this.id ;
    }
    public String getCorporationInfo() {
        return this.corporationInfo ;
    }
    public String getServiceCenter() {
        return this.serviceCenter ;
    }

}
