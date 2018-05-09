package com.stxx.louvre.entity;

/**
 * description: 上传头像
 * Created by liNan on 2018/5/8 10:25
 */
public class UploadIconRespBean {


    /**
     * msg : http://image.hmlfg.net/headinfo/186029285141525745665871.png
     * state : SUCCESS
     * type : 0
     * url : http://image.hmlfg.net/headinfo/186029285141525745665871.png
     */

    private String msg;
    private String state;
    private String type;
    private String url;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
