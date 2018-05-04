package com.stxx.louvre.entity;

import java.util.List;

/**
 * description:
 * Created by liNan on 2018/5/2 15:50
 */
public class ConfirmOrderListRespBean {

    /**
     * number : 1
     * postage : 0
     * code : 0
     * totalprice : 260000.0
     * idlist : ["21013a1cbaba48dcbe568e2c4103130c"]
     * lessprice : 0
     */

    private int number;
    private int postage;
    private int code;
    private String totalprice;
    private int lessprice;
    private List<String> idlist;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public int getLessprice() {
        return lessprice;
    }

    public void setLessprice(int lessprice) {
        this.lessprice = lessprice;
    }

    public List<String> getIdlist() {
        return idlist;
    }

    public void setIdlist(List<String> idlist) {
        this.idlist = idlist;
    }
}
