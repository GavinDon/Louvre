package com.stxx.louvre.entity;

import java.util.List;

/**
 * description:
 * Created by liNan on 2018/4/23 14:37
 */
public class HomeLfgBean {


    /**
     * first : 1
     * limit : 2147483647
     * offset : 0
     * pageNo : 1
     * pageSize : 20
     * rows : [{"context":"lijielijie","ctime":"2018-04-18 16:58:52","flag":1,"id":14,"imgurl":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1524041941469.png","name":"lijie","up":"1","utime":"2018-04-18 17:13:16"},{"context":"dadsadsad","ctime":"2018-04-18 16:56:14","flag":1,"id":13,"imgurl":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1524041781137.png","name":"dsadsa","up":"1","utime":"2018-04-18 16:56:14"},{"context":"dasda","ctime":"2018-04-18 15:35:27","flag":1,"id":11,"imgurl":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1524036945502.png","name":"科普馆","up":"1","utime":"2018-04-18 15:35:27"}]
     * total : 3
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
         * context : lijielijie
         * ctime : 2018-04-18 16:58:52
         * flag : 1
         * id : 14
         * imgurl : https://membersbucket.oss-cn-huhehaote.aliyuncs.com/image/1524041941469.png
         * name : lijie
         * up : 1
         * utime : 2018-04-18 17:13:16
         */

        private String context;
        private String ctime;
        private int flag;
        private int id;
        private String imgurl;
        private String name;
        private String up;
        private String utime;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }
    }
}
