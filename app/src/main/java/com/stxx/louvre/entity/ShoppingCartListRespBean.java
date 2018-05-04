package com.stxx.louvre.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * description: 购物车列表
 * Created by liNan on 2018/4/27 17:04
 */
public class ShoppingCartListRespBean {


    /**
     * first : 1
     * limit : 2147483647
     * offset : 0
     * pageNo : 1
     * pageSize : 1000
     * rows : [{"PERMISSIONS":"","PICTURE":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1523783898392.png","FORMAT":"2","OWNERSHIP_OF_GOODS":0,"S_AUTHOR":"9f41957fc02b4aa08928cd60d5bfcaac","USER_ID":"15091776297","Count":10,"REC_TYPE":"3","DEPOSIT":"32","IF_LEASE":1,"CLASS_NAME":"行书","SHORT_NAME":"三尺加长","MODIFY_ID":"11","STATUS":0,"ARTIST_CreativeTime":"2017-09-27 00:00:00","MODIFY_DATE":"2018-04-17 14:39:29","KEY_WORDS":"中,","APPLICANT_TIME":"2018-04-15 20:55:26","ID":"4ac038e711a0413ea2e383bddf652662","ARTIST_NAME":"史永哲","IF_WANFU":1,"RENT":"12","HITS":7,"IF_FORYOU":0,"NUMBER":10,"PAR_ID":"300c435801304a92ba4c96c10b593cb7","PID":"6bdaf7aafe754da49052834055bf3af4","THUMBNAIL":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1523796958008.png","S_KONGJIAN":"0","PRODUCT_ID":"6bdaf7aafe754da49052834055bf3af4","IS_STAR":"1","DISCOUNT":"8","CREATE_DATE":"2018-04-15 17:18:23","MODELS":"1523783825000","CONTS":"此书法展现了深厚的书法功底。","DISTRIBUTION_MODE":"0","P_NAME":"爱国爱家","OLD_PRICE":"3217","IS_ONLINE":2,"OWNERSHIP":"","CREATE_ID":"1"}]
     * total : 1
     * totalPages : 1
     */

    private int first;
    private int limit;
    private int offset;
    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPages;
    private List<RowsBean> rows;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Parcelable{
        /**
         * PERMISSIONS :
         * PICTURE : https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1523783898392.png
         * FORMAT : 2
         * OWNERSHIP_OF_GOODS : 0
         * S_AUTHOR : 9f41957fc02b4aa08928cd60d5bfcaac
         * USER_ID : 15091776297
         * Count : 10
         * REC_TYPE : 3
         * DEPOSIT : 32
         * IF_LEASE : 1
         * CLASS_NAME : 行书
         * SHORT_NAME : 三尺加长
         * MODIFY_ID : 11
         * STATUS : 0
         * ARTIST_CreativeTime : 2017-09-27 00:00:00
         * MODIFY_DATE : 2018-04-17 14:39:29
         * KEY_WORDS : 中,
         * APPLICANT_TIME : 2018-04-15 20:55:26
         * ID : 4ac038e711a0413ea2e383bddf652662
         * ARTIST_NAME : 史永哲
         * IF_WANFU : 1
         * RENT : 12
         * HITS : 7
         * IF_FORYOU : 0
         * NUMBER : 10
         * PAR_ID : 300c435801304a92ba4c96c10b593cb7
         * PID : 6bdaf7aafe754da49052834055bf3af4
         * THUMBNAIL : https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1523796958008.png
         * S_KONGJIAN : 0
         * PRODUCT_ID : 6bdaf7aafe754da49052834055bf3af4
         * IS_STAR : 1
         * DISCOUNT : 8
         * CREATE_DATE : 2018-04-15 17:18:23
         * MODELS : 1523783825000
         * CONTS : 此书法展现了深厚的书法功底。
         * DISTRIBUTION_MODE : 0
         * P_NAME : 爱国爱家
         * OLD_PRICE : 3217
         * IS_ONLINE : 2
         * OWNERSHIP :
         * CREATE_ID : 1
         */

        private String PERMISSIONS;
        private String PICTURE;
        private String FORMAT;
        private int OWNERSHIP_OF_GOODS;
        private String S_AUTHOR;
        private String USER_ID;
        private int Count;
        private String REC_TYPE;
        private String DEPOSIT;
        private int IF_LEASE;
        private String CLASS_NAME;
        private String SHORT_NAME;
        private String MODIFY_ID;
        private int STATUS;
        private String ARTIST_CreativeTime;
        private String MODIFY_DATE;
        private String KEY_WORDS;
        private String APPLICANT_TIME;
        private String ID;
        private String ARTIST_NAME;
        private int IF_WANFU;
        private String RENT;
        private int HITS;
        private int IF_FORYOU;
        private int NUMBER;
        private String PAR_ID;
        private String PID;
        private String THUMBNAIL;
        private String S_KONGJIAN;
        private String PRODUCT_ID;
        private String IS_STAR;
        private String DISCOUNT;
        private String CREATE_DATE;
        private String MODELS;
        private String CONTS;
        private String DISTRIBUTION_MODE;
        private String P_NAME;
        private String OLD_PRICE;
        private int IS_ONLINE;
        private String OWNERSHIP;
        private String CREATE_ID;

        protected RowsBean(Parcel in) {
            PERMISSIONS = in.readString();
            PICTURE = in.readString();
            FORMAT = in.readString();
            OWNERSHIP_OF_GOODS = in.readInt();
            S_AUTHOR = in.readString();
            USER_ID = in.readString();
            Count = in.readInt();
            REC_TYPE = in.readString();
            DEPOSIT = in.readString();
            IF_LEASE = in.readInt();
            CLASS_NAME = in.readString();
            SHORT_NAME = in.readString();
            MODIFY_ID = in.readString();
            STATUS = in.readInt();
            ARTIST_CreativeTime = in.readString();
            MODIFY_DATE = in.readString();
            KEY_WORDS = in.readString();
            APPLICANT_TIME = in.readString();
            ID = in.readString();
            ARTIST_NAME = in.readString();
            IF_WANFU = in.readInt();
            RENT = in.readString();
            HITS = in.readInt();
            IF_FORYOU = in.readInt();
            NUMBER = in.readInt();
            PAR_ID = in.readString();
            PID = in.readString();
            THUMBNAIL = in.readString();
            S_KONGJIAN = in.readString();
            PRODUCT_ID = in.readString();
            IS_STAR = in.readString();
            DISCOUNT = in.readString();
            CREATE_DATE = in.readString();
            MODELS = in.readString();
            CONTS = in.readString();
            DISTRIBUTION_MODE = in.readString();
            P_NAME = in.readString();
            OLD_PRICE = in.readString();
            IS_ONLINE = in.readInt();
            OWNERSHIP = in.readString();
            CREATE_ID = in.readString();
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel in) {
                return new RowsBean(in);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };

        public String getPERMISSIONS() {
            return PERMISSIONS;
        }

        public void setPERMISSIONS(String PERMISSIONS) {
            this.PERMISSIONS = PERMISSIONS;
        }

        public String getPICTURE() {
            return PICTURE;
        }

        public void setPICTURE(String PICTURE) {
            this.PICTURE = PICTURE;
        }

        public String getFORMAT() {
            return FORMAT;
        }

        public void setFORMAT(String FORMAT) {
            this.FORMAT = FORMAT;
        }

        public int getOWNERSHIP_OF_GOODS() {
            return OWNERSHIP_OF_GOODS;
        }

        public void setOWNERSHIP_OF_GOODS(int OWNERSHIP_OF_GOODS) {
            this.OWNERSHIP_OF_GOODS = OWNERSHIP_OF_GOODS;
        }

        public String getS_AUTHOR() {
            return S_AUTHOR;
        }

        public void setS_AUTHOR(String S_AUTHOR) {
            this.S_AUTHOR = S_AUTHOR;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public String getREC_TYPE() {
            return REC_TYPE;
        }

        public void setREC_TYPE(String REC_TYPE) {
            this.REC_TYPE = REC_TYPE;
        }

        public String getDEPOSIT() {
            return DEPOSIT;
        }

        public void setDEPOSIT(String DEPOSIT) {
            this.DEPOSIT = DEPOSIT;
        }

        public int getIF_LEASE() {
            return IF_LEASE;
        }

        public void setIF_LEASE(int IF_LEASE) {
            this.IF_LEASE = IF_LEASE;
        }

        public String getCLASS_NAME() {
            return CLASS_NAME;
        }

        public void setCLASS_NAME(String CLASS_NAME) {
            this.CLASS_NAME = CLASS_NAME;
        }

        public String getSHORT_NAME() {
            return SHORT_NAME;
        }

        public void setSHORT_NAME(String SHORT_NAME) {
            this.SHORT_NAME = SHORT_NAME;
        }

        public String getMODIFY_ID() {
            return MODIFY_ID;
        }

        public void setMODIFY_ID(String MODIFY_ID) {
            this.MODIFY_ID = MODIFY_ID;
        }

        public int getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(int STATUS) {
            this.STATUS = STATUS;
        }

        public String getARTIST_CreativeTime() {
            return ARTIST_CreativeTime;
        }

        public void setARTIST_CreativeTime(String ARTIST_CreativeTime) {
            this.ARTIST_CreativeTime = ARTIST_CreativeTime;
        }

        public String getMODIFY_DATE() {
            return MODIFY_DATE;
        }

        public void setMODIFY_DATE(String MODIFY_DATE) {
            this.MODIFY_DATE = MODIFY_DATE;
        }

        public String getKEY_WORDS() {
            return KEY_WORDS;
        }

        public void setKEY_WORDS(String KEY_WORDS) {
            this.KEY_WORDS = KEY_WORDS;
        }

        public String getAPPLICANT_TIME() {
            return APPLICANT_TIME;
        }

        public void setAPPLICANT_TIME(String APPLICANT_TIME) {
            this.APPLICANT_TIME = APPLICANT_TIME;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getARTIST_NAME() {
            return ARTIST_NAME;
        }

        public void setARTIST_NAME(String ARTIST_NAME) {
            this.ARTIST_NAME = ARTIST_NAME;
        }

        public int getIF_WANFU() {
            return IF_WANFU;
        }

        public void setIF_WANFU(int IF_WANFU) {
            this.IF_WANFU = IF_WANFU;
        }

        public String getRENT() {
            return RENT;
        }

        public void setRENT(String RENT) {
            this.RENT = RENT;
        }

        public int getHITS() {
            return HITS;
        }

        public void setHITS(int HITS) {
            this.HITS = HITS;
        }

        public int getIF_FORYOU() {
            return IF_FORYOU;
        }

        public void setIF_FORYOU(int IF_FORYOU) {
            this.IF_FORYOU = IF_FORYOU;
        }

        public int getNUMBER() {
            return NUMBER;
        }

        public void setNUMBER(int NUMBER) {
            this.NUMBER = NUMBER;
        }

        public String getPAR_ID() {
            return PAR_ID;
        }

        public void setPAR_ID(String PAR_ID) {
            this.PAR_ID = PAR_ID;
        }

        public String getPID() {
            return PID;
        }

        public void setPID(String PID) {
            this.PID = PID;
        }

        public String getTHUMBNAIL() {
            return THUMBNAIL;
        }

        public void setTHUMBNAIL(String THUMBNAIL) {
            this.THUMBNAIL = THUMBNAIL;
        }

        public String getS_KONGJIAN() {
            return S_KONGJIAN;
        }

        public void setS_KONGJIAN(String S_KONGJIAN) {
            this.S_KONGJIAN = S_KONGJIAN;
        }

        public String getPRODUCT_ID() {
            return PRODUCT_ID;
        }

        public void setPRODUCT_ID(String PRODUCT_ID) {
            this.PRODUCT_ID = PRODUCT_ID;
        }

        public String getIS_STAR() {
            return IS_STAR;
        }

        public void setIS_STAR(String IS_STAR) {
            this.IS_STAR = IS_STAR;
        }

        public String getDISCOUNT() {
            return DISCOUNT;
        }

        public void setDISCOUNT(String DISCOUNT) {
            this.DISCOUNT = DISCOUNT;
        }

        public String getCREATE_DATE() {
            return CREATE_DATE;
        }

        public void setCREATE_DATE(String CREATE_DATE) {
            this.CREATE_DATE = CREATE_DATE;
        }

        public String getMODELS() {
            return MODELS;
        }

        public void setMODELS(String MODELS) {
            this.MODELS = MODELS;
        }

        public String getCONTS() {
            return CONTS;
        }

        public void setCONTS(String CONTS) {
            this.CONTS = CONTS;
        }

        public String getDISTRIBUTION_MODE() {
            return DISTRIBUTION_MODE;
        }

        public void setDISTRIBUTION_MODE(String DISTRIBUTION_MODE) {
            this.DISTRIBUTION_MODE = DISTRIBUTION_MODE;
        }

        public String getP_NAME() {
            return P_NAME;
        }

        public void setP_NAME(String P_NAME) {
            this.P_NAME = P_NAME;
        }

        public String getOLD_PRICE() {
            return OLD_PRICE;
        }

        public void setOLD_PRICE(String OLD_PRICE) {
            this.OLD_PRICE = OLD_PRICE;
        }

        public int getIS_ONLINE() {
            return IS_ONLINE;
        }

        public void setIS_ONLINE(int IS_ONLINE) {
            this.IS_ONLINE = IS_ONLINE;
        }

        public String getOWNERSHIP() {
            return OWNERSHIP;
        }

        public void setOWNERSHIP(String OWNERSHIP) {
            this.OWNERSHIP = OWNERSHIP;
        }

        public String getCREATE_ID() {
            return CREATE_ID;
        }

        public void setCREATE_ID(String CREATE_ID) {
            this.CREATE_ID = CREATE_ID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(PERMISSIONS);
            dest.writeString(PICTURE);
            dest.writeString(FORMAT);
            dest.writeInt(OWNERSHIP_OF_GOODS);
            dest.writeString(S_AUTHOR);
            dest.writeString(USER_ID);
            dest.writeInt(Count);
            dest.writeString(REC_TYPE);
            dest.writeString(DEPOSIT);
            dest.writeInt(IF_LEASE);
            dest.writeString(CLASS_NAME);
            dest.writeString(SHORT_NAME);
            dest.writeString(MODIFY_ID);
            dest.writeInt(STATUS);
            dest.writeString(ARTIST_CreativeTime);
            dest.writeString(MODIFY_DATE);
            dest.writeString(KEY_WORDS);
            dest.writeString(APPLICANT_TIME);
            dest.writeString(ID);
            dest.writeString(ARTIST_NAME);
            dest.writeInt(IF_WANFU);
            dest.writeString(RENT);
            dest.writeInt(HITS);
            dest.writeInt(IF_FORYOU);
            dest.writeInt(NUMBER);
            dest.writeString(PAR_ID);
            dest.writeString(PID);
            dest.writeString(THUMBNAIL);
            dest.writeString(S_KONGJIAN);
            dest.writeString(PRODUCT_ID);
            dest.writeString(IS_STAR);
            dest.writeString(DISCOUNT);
            dest.writeString(CREATE_DATE);
            dest.writeString(MODELS);
            dest.writeString(CONTS);
            dest.writeString(DISTRIBUTION_MODE);
            dest.writeString(P_NAME);
            dest.writeString(OLD_PRICE);
            dest.writeInt(IS_ONLINE);
            dest.writeString(OWNERSHIP);
            dest.writeString(CREATE_ID);
        }
    }
}
