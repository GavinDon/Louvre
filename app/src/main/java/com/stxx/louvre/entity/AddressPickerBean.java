package com.stxx.louvre.entity;

import java.util.List;

/**
 * description: address.json对应bean
 * Created by liNan on 2018/3/12 9:15
 */

public class AddressPickerBean {

    private List<AddressItemBean> province;
    private List<AddressItemBean> city;

    public List<AddressItemBean> getProvince() {
        return province;
    }

    public void setProvince(List<AddressItemBean> province) {
        this.province = province;
    }

    public List<AddressItemBean> getCity() {
        return city;
    }

    public void setCity(List<AddressItemBean> city) {
        this.city = city;
    }

    public List<AddressItemBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<AddressItemBean> district) {
        this.district = district;
    }

    private List<AddressItemBean> district;



    public static class AddressItemBean {
        /**
         * i : 86340000
         * n : 安徽省
         * p : 86000000
         */

        private String i;
        private String n;
        private String p;

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

}
