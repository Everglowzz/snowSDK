package co.tiangongsky.bxsdkdemo.bean;
/**
 * Created by EverGlow on 2019/10/31 16:44
 */
public    class skip {
    
    
    public int control;
    public String appUrl;

    public int getControl() {
        return  control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public String getAppUrl() {
        return appUrl == null ? "" : appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
