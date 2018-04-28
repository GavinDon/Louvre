package com.stxx.louvre.entity;

import java.util.List;

/**
 * description:
 * Created by liNan on 2018/4/27 11:09
 */
public class ProtfoloListBean {


    /**
     * first : 1
     * limit : 2147483647
     * offset : 0
     * pageNo : 1
     * pageSize : 1
     * rows : [{"actId":"","addKeyWords":"","applicant":"","applicantReason":"","applicantTime":null,"artistCreativeTime":null,"artistName":"柏佳甫","certificate":"","className":"人物","conts":"完美商品","createDate":null,"createId":"","decorationChiCun":"","deposit":"3","discount":"","distributionMode":"0","distributorDiscount":"","distributorLevel":"","dz":"1","fl":"1","format":"","fullName":"","hits":null,"id":"XBJFH0003","ifForyou":0,"ifLease":0,"ifWanfu":0,"imgHeight":1280,"imgWidth":931,"isDel":0,"isDz":"0","isOnline":2,"isRecommend":"1","isStar":"1","keyWords":"","limitPrice":"","limitType":null,"localGraph1":"","localGraph2":"","localGraph3":"","models":"XBJFH0003","modifyDate":"2018-04-26 19:37:56","modifyId":"","number":9,"oldPrice":"480000","onLineTime":null,"ownership":"","ownershipName":null,"ownershipOfGoods":0,"pName":"瓷韵","paintBucket":null,"parId":"742d7c0b22184d688c47f88dc564dc24","permissions":"","picture":"http://image.hmlfg.net/image/GQ00199.jpg","positionNumber":null,"price":"","promotionEnd":null,"promotionStart":null,"recType":"","rent":"2","sAuthor":"201804230117","sChicun":"","sKongjian":"客厅,餐厅,书房,卧室","sPrice":"","sales":null,"shopShelves":null,"shortName":"其他","status":0,"swxn":"","thumbnail":"http://image.hmlfg.net/image/SL00199.jpg","wf":"1","zl":"1"}]
     * total : 517
     * totalPages : 517
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

    public static class RowsBean {
        /**
         * actId :
         * addKeyWords :
         * applicant :
         * applicantReason :
         * applicantTime : null
         * artistCreativeTime : null
         * artistName : 柏佳甫
         * certificate :
         * className : 人物
         * conts : 完美商品
         * createDate : null
         * createId :
         * decorationChiCun :
         * deposit : 3
         * discount :
         * distributionMode : 0
         * distributorDiscount :
         * distributorLevel :
         * dz : 1
         * fl : 1
         * format :
         * fullName :
         * hits : null
         * id : XBJFH0003
         * ifForyou : 0
         * ifLease : 0
         * ifWanfu : 0
         * imgHeight : 1280
         * imgWidth : 931
         * isDel : 0
         * isDz : 0
         * isOnline : 2
         * isRecommend : 1
         * isStar : 1
         * keyWords :
         * limitPrice :
         * limitType : null
         * localGraph1 :
         * localGraph2 :
         * localGraph3 :
         * models : XBJFH0003
         * modifyDate : 2018-04-26 19:37:56
         * modifyId :
         * number : 9
         * oldPrice : 480000
         * onLineTime : null
         * ownership :
         * ownershipName : null
         * ownershipOfGoods : 0
         * pName : 瓷韵
         * paintBucket : null
         * parId : 742d7c0b22184d688c47f88dc564dc24
         * permissions :
         * picture : http://image.hmlfg.net/image/GQ00199.jpg
         * positionNumber : null
         * price :
         * promotionEnd : null
         * promotionStart : null
         * recType :
         * rent : 2
         * sAuthor : 201804230117
         * sChicun :
         * sKongjian : 客厅,餐厅,书房,卧室
         * sPrice :
         * sales : null
         * shopShelves : null
         * shortName : 其他
         * status : 0
         * swxn :
         * thumbnail : http://image.hmlfg.net/image/SL00199.jpg
         * wf : 1
         * zl : 1
         */

        private String actId;
        private String addKeyWords;
        private String applicant;
        private String applicantReason;
        private Object applicantTime;
        private Object artistCreativeTime;
        private String artistName;
        private String certificate;
        private String className;
        private String conts;
        private Object createDate;
        private String createId;
        private String decorationChiCun;
        private String deposit;
        private String discount;
        private String distributionMode;
        private String distributorDiscount;
        private String distributorLevel;
        private String dz;
        private String fl;
        private String format;
        private String fullName;
        private Object hits;
        private String id;
        private int ifForyou;
        private int ifLease;
        private int ifWanfu;
        private int imgHeight;
        private int imgWidth;
        private int isDel;
        private String isDz;
        private int isOnline;
        private String isRecommend;
        private String isStar;
        private String keyWords;
        private String limitPrice;
        private Object limitType;
        private String localGraph1;
        private String localGraph2;
        private String localGraph3;
        private String models;
        private String modifyDate;
        private String modifyId;
        private int number;
        private String oldPrice;
        private Object onLineTime;
        private String ownership;
        private Object ownershipName;
        private int ownershipOfGoods;
        private String pName;
        private Object paintBucket;
        private String parId;
        private String permissions;
        private String picture;
        private Object positionNumber;
        private String price;
        private Object promotionEnd;
        private Object promotionStart;
        private String recType;
        private String rent;
        private String sAuthor;
        private String sChicun;
        private String sKongjian;
        private String sPrice;
        private Object sales;
        private Object shopShelves;
        private String shortName;
        private int status;
        private String swxn;
        private String thumbnail;
        private String wf;
        private String zl;

        public String getActId() {
            return actId;
        }

        public void setActId(String actId) {
            this.actId = actId;
        }

        public String getAddKeyWords() {
            return addKeyWords;
        }

        public void setAddKeyWords(String addKeyWords) {
            this.addKeyWords = addKeyWords;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public String getApplicantReason() {
            return applicantReason;
        }

        public void setApplicantReason(String applicantReason) {
            this.applicantReason = applicantReason;
        }

        public Object getApplicantTime() {
            return applicantTime;
        }

        public void setApplicantTime(Object applicantTime) {
            this.applicantTime = applicantTime;
        }

        public Object getArtistCreativeTime() {
            return artistCreativeTime;
        }

        public void setArtistCreativeTime(Object artistCreativeTime) {
            this.artistCreativeTime = artistCreativeTime;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getConts() {
            return conts;
        }

        public void setConts(String conts) {
            this.conts = conts;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getDecorationChiCun() {
            return decorationChiCun;
        }

        public void setDecorationChiCun(String decorationChiCun) {
            this.decorationChiCun = decorationChiCun;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDistributionMode() {
            return distributionMode;
        }

        public void setDistributionMode(String distributionMode) {
            this.distributionMode = distributionMode;
        }

        public String getDistributorDiscount() {
            return distributorDiscount;
        }

        public void setDistributorDiscount(String distributorDiscount) {
            this.distributorDiscount = distributorDiscount;
        }

        public String getDistributorLevel() {
            return distributorLevel;
        }

        public void setDistributorLevel(String distributorLevel) {
            this.distributorLevel = distributorLevel;
        }

        public String getDz() {
            return dz;
        }

        public void setDz(String dz) {
            this.dz = dz;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Object getHits() {
            return hits;
        }

        public void setHits(Object hits) {
            this.hits = hits;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIfForyou() {
            return ifForyou;
        }

        public void setIfForyou(int ifForyou) {
            this.ifForyou = ifForyou;
        }

        public int getIfLease() {
            return ifLease;
        }

        public void setIfLease(int ifLease) {
            this.ifLease = ifLease;
        }

        public int getIfWanfu() {
            return ifWanfu;
        }

        public void setIfWanfu(int ifWanfu) {
            this.ifWanfu = ifWanfu;
        }

        public int getImgHeight() {
            return imgHeight;
        }

        public void setImgHeight(int imgHeight) {
            this.imgHeight = imgHeight;
        }

        public int getImgWidth() {
            return imgWidth;
        }

        public void setImgWidth(int imgWidth) {
            this.imgWidth = imgWidth;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public String getIsDz() {
            return isDz;
        }

        public void setIsDz(String isDz) {
            this.isDz = isDz;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public String getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(String isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getIsStar() {
            return isStar;
        }

        public void setIsStar(String isStar) {
            this.isStar = isStar;
        }

        public String getKeyWords() {
            return keyWords;
        }

        public void setKeyWords(String keyWords) {
            this.keyWords = keyWords;
        }

        public String getLimitPrice() {
            return limitPrice;
        }

        public void setLimitPrice(String limitPrice) {
            this.limitPrice = limitPrice;
        }

        public Object getLimitType() {
            return limitType;
        }

        public void setLimitType(Object limitType) {
            this.limitType = limitType;
        }

        public String getLocalGraph1() {
            return localGraph1;
        }

        public void setLocalGraph1(String localGraph1) {
            this.localGraph1 = localGraph1;
        }

        public String getLocalGraph2() {
            return localGraph2;
        }

        public void setLocalGraph2(String localGraph2) {
            this.localGraph2 = localGraph2;
        }

        public String getLocalGraph3() {
            return localGraph3;
        }

        public void setLocalGraph3(String localGraph3) {
            this.localGraph3 = localGraph3;
        }

        public String getModels() {
            return models;
        }

        public void setModels(String models) {
            this.models = models;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getModifyId() {
            return modifyId;
        }

        public void setModifyId(String modifyId) {
            this.modifyId = modifyId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public Object getOnLineTime() {
            return onLineTime;
        }

        public void setOnLineTime(Object onLineTime) {
            this.onLineTime = onLineTime;
        }

        public String getOwnership() {
            return ownership;
        }

        public void setOwnership(String ownership) {
            this.ownership = ownership;
        }

        public Object getOwnershipName() {
            return ownershipName;
        }

        public void setOwnershipName(Object ownershipName) {
            this.ownershipName = ownershipName;
        }

        public int getOwnershipOfGoods() {
            return ownershipOfGoods;
        }

        public void setOwnershipOfGoods(int ownershipOfGoods) {
            this.ownershipOfGoods = ownershipOfGoods;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public Object getPaintBucket() {
            return paintBucket;
        }

        public void setPaintBucket(Object paintBucket) {
            this.paintBucket = paintBucket;
        }

        public String getParId() {
            return parId;
        }

        public void setParId(String parId) {
            this.parId = parId;
        }

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Object getPositionNumber() {
            return positionNumber;
        }

        public void setPositionNumber(Object positionNumber) {
            this.positionNumber = positionNumber;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getPromotionEnd() {
            return promotionEnd;
        }

        public void setPromotionEnd(Object promotionEnd) {
            this.promotionEnd = promotionEnd;
        }

        public Object getPromotionStart() {
            return promotionStart;
        }

        public void setPromotionStart(Object promotionStart) {
            this.promotionStart = promotionStart;
        }

        public String getRecType() {
            return recType;
        }

        public void setRecType(String recType) {
            this.recType = recType;
        }

        public String getRent() {
            return rent;
        }

        public void setRent(String rent) {
            this.rent = rent;
        }

        public String getSAuthor() {
            return sAuthor;
        }

        public void setSAuthor(String sAuthor) {
            this.sAuthor = sAuthor;
        }

        public String getSChicun() {
            return sChicun;
        }

        public void setSChicun(String sChicun) {
            this.sChicun = sChicun;
        }

        public String getSKongjian() {
            return sKongjian;
        }

        public void setSKongjian(String sKongjian) {
            this.sKongjian = sKongjian;
        }

        public String getSPrice() {
            return sPrice;
        }

        public void setSPrice(String sPrice) {
            this.sPrice = sPrice;
        }

        public Object getSales() {
            return sales;
        }

        public void setSales(Object sales) {
            this.sales = sales;
        }

        public Object getShopShelves() {
            return shopShelves;
        }

        public void setShopShelves(Object shopShelves) {
            this.shopShelves = shopShelves;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSwxn() {
            return swxn;
        }

        public void setSwxn(String swxn) {
            this.swxn = swxn;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getWf() {
            return wf;
        }

        public void setWf(String wf) {
            this.wf = wf;
        }

        public String getZl() {
            return zl;
        }

        public void setZl(String zl) {
            this.zl = zl;
        }
    }
}
