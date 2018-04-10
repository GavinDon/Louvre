package com.stxx.louvre.entity;

import java.util.List;

/**
 * description:
 * Created by liNan on 2018/4/10 15:07
 */
public class ClassifyBean {


    /**
     * code : 0
     * data : [{"leftName":"国画","right":[{"detail":[{"imgUrl":"","name":"写意"},{"imgUrl":"","name":"工笔"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}],"title":"推荐类目"},{"detail":[{"imgUrl":"","name":"写意"},{"imgUrl":"","name":"工笔"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}],"title":"其他类目"}]},{"leftName":"书法","right":[{"detail":[{"imgUrl":"","name":"楷书"},{"imgUrl":"","name":"行书"},{"imgUrl":"","name":"草书"},{"imgUrl":"","name":"隶书"},{"imgUrl":"","name":"篆书"},{"imgUrl":"","name":"魏碑"},{"imgUrl":"","name":"篆书"}],"title":"推荐类目"},{"detail":[{"imgUrl":"","name":"写篆书"},{"imgUrl":"","name":"魏碑"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}],"title":"其他类目"}]}]
     * msg : 成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * leftName : 国画
         * right : [{"detail":[{"imgUrl":"","name":"写意"},{"imgUrl":"","name":"工笔"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}],"title":"推荐类目"},{"detail":[{"imgUrl":"","name":"写意"},{"imgUrl":"","name":"工笔"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}],"title":"其他类目"}]
         */

        private String leftName;
        private List<RightBean> right;

        public String getLeftName() {
            return leftName;
        }

        public void setLeftName(String leftName) {
            this.leftName = leftName;
        }

        public List<RightBean> getRight() {
            return right;
        }

        public void setRight(List<RightBean> right) {
            this.right = right;
        }

        public static class RightBean {
            /**
             * detail : [{"imgUrl":"","name":"写意"},{"imgUrl":"","name":"工笔"},{"imgUrl":"","name":"重彩"},{"imgUrl":"","name":"白描"},{"imgUrl":"","name":"兼工带写"}]
             * title : 推荐类目
             */

            private String title;
            private List<DetailBean> detail;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DetailBean> getDetail() {
                return detail;
            }

            public void setDetail(List<DetailBean> detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * imgUrl :
                 * name : 写意
                 */

                private String imgUrl;
                private String name;

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
