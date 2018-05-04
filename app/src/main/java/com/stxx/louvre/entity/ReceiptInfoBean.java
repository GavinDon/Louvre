package com.stxx.louvre.entity;

import java.util.List;

/**
 * description:发票
 * Created by liNan on 2018/5/3 17:03
 */
public class ReceiptInfoBean {

    /**
     * first : 1
     * limit : 2147483647
     * offset : 0
     * pageNo : 1
     * pageSize : 20
     * rows : [{"bank":"交通银行","bankAccount":"21344534543543","billingTime":null,"companyAddress":"劳动南路","createDate":null,"createId":"13279348371","dutyParagraph":"01111101","id":"a172cbba3c4144c090bfbd824956ad51","invoiceAmount":null,"invoicenNumber":null,"isdefalurt":null,"modifyDate":null,"modifyId":null,"name":"中通服","orderId":null,"remark":null,"status":0,"tel":"029-2213122","type":1,"userId":"13279348371"},{"bank":"交通银行","bankAccount":"21344534543543","billingTime":null,"companyAddress":"劳动南路","createDate":null,"createId":"13279348371","dutyParagraph":"01111101","id":"2cc950e6d0ed4faa8dfa9abe8540a5af","invoiceAmount":null,"invoicenNumber":null,"isdefalurt":null,"modifyDate":null,"modifyId":null,"name":"中通服","orderId":null,"remark":null,"status":0,"tel":"029-2213122","type":1,"userId":"13279348371"}]
     * total : 2
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

    public static class RowsBean {
        /**
         * bank : 交通银行
         * bankAccount : 21344534543543
         * billingTime : null
         * companyAddress : 劳动南路
         * createDate : null
         * createId : 13279348371
         * dutyParagraph : 01111101
         * id : a172cbba3c4144c090bfbd824956ad51
         * invoiceAmount : null
         * invoicenNumber : null
         * isdefalurt : null
         * modifyDate : null
         * modifyId : null
         * name : 中通服
         * orderId : null
         * remark : null
         * status : 0
         * tel : 029-2213122
         * type : 1
         * userId : 13279348371
         */

        private String bank;
        private String bankAccount;
        private Object billingTime;
        private String companyAddress;
        private Object createDate;
        private String createId;
        private String dutyParagraph;
        private String id;
        private Object invoiceAmount;
        private Object invoicenNumber;
        private Object isdefalurt;
        private Object modifyDate;
        private Object modifyId;
        private String name;
        private Object orderId;
        private Object remark;
        private int status;
        private String tel;
        private int type;
        private String userId;

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public Object getBillingTime() {
            return billingTime;
        }

        public void setBillingTime(Object billingTime) {
            this.billingTime = billingTime;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
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

        public String getDutyParagraph() {
            return dutyParagraph;
        }

        public void setDutyParagraph(String dutyParagraph) {
            this.dutyParagraph = dutyParagraph;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(Object invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public Object getInvoicenNumber() {
            return invoicenNumber;
        }

        public void setInvoicenNumber(Object invoicenNumber) {
            this.invoicenNumber = invoicenNumber;
        }

        public Object getIsdefalurt() {
            return isdefalurt;
        }

        public void setIsdefalurt(Object isdefalurt) {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
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
