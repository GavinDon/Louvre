package com.stxx.louvre.entity;

/**
 * description:
 * Created by liNan on 2018/3/2 15:55
 */

public class RecommendItemBean {
    public RecommendItemBean(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private  String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
