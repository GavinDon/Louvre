package com.stxx.louvre.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * description: 地址列表实体类
 * Created by liNan on 2018/4/18 10:56
 */
public class AddressListBean implements Parcelable {


    /**
     * first : 1
     * limit : 2147483647
     * offset : 0
     * pageNo : 1
     * pageSize : 20
     * rows : [{"address":"南五十家子镇远安制药厂","area":null,"city":"承德市","createDate":"2018-04-18 15:44:05","createId":"17792215701","id":"11f9c7084f144a688f2a99a776d6104a","isdefalurt":1,"modifyDate":null,"modifyId":null,"phone":"联系方式:","province":"河北省","shippingMethod":null,"spareTel":null,"status":0,"trueName":"钉钉DING消息","type":null,"userId":"17792215701"}]
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

    protected AddressListBean(Parcel in) {
        first = in.readInt();
        limit = in.readInt();
        offset = in.readInt();
        pageNo = in.readInt();
        pageSize = in.readInt();
        total = in.readInt();
        totalPages = in.readInt();
    }

    public static final Creator<AddressListBean> CREATOR = new Creator<AddressListBean>() {
        @Override
        public AddressListBean createFromParcel(Parcel in) {
            return new AddressListBean(in);
        }

        @Override
        public AddressListBean[] newArray(int size) {
            return new AddressListBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(first);
        dest.writeInt(limit);
        dest.writeInt(offset);
        dest.writeInt(pageNo);
        dest.writeInt(pageSize);
        dest.writeInt(total);
        dest.writeInt(totalPages);
    }

    public static class RowsBean {
        /**
         * address : 南五十家子镇远安制药厂
         * area : null
         * city : 承德市
         * createDate : 2018-04-18 15:44:05
         * createId : 17792215701
         * id : 11f9c7084f144a688f2a99a776d6104a
         * isdefalurt : 1
         * modifyDate : null
         * modifyId : null
         * phone : 联系方式:
         * province : 河北省
         * shippingMethod : null
         * spareTel : null
         * status : 0
         * trueName : 钉钉DING消息
         * type : null
         * userId : 17792215701
         */

        private String address;
        private Object area;
        private String city;
        private String createDate;
        private String createId;
        private String id;
        private int isdefalurt;
        private Object modifyDate;
        private Object modifyId;
        private String phone;
        private String province;
        private Object shippingMethod;
        private Object spareTel;
        private int status;
        private String trueName;
        private Object type;
        private String userId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsdefalurt() {
            return isdefalurt;
        }

        public void setIsdefalurt(int isdefalurt) {
            this.isdefalurt = isdefalurt;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getModifyId() {
            return modifyId;
        }

        public void setModifyId(Object modifyId) {
            this.modifyId = modifyId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Object getShippingMethod() {
            return shippingMethod;
        }

        public void setShippingMethod(Object shippingMethod) {
            this.shippingMethod = shippingMethod;
        }

        public Object getSpareTel() {
            return spareTel;
        }

        public void setSpareTel(Object spareTel) {
            this.spareTel = spareTel;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
