package com.stxx.louvre.entity;

/**
 * description: 个人信息
 * Created by liNan on 2018/5/4 10:41
 */
public class UserInfoBean {


    /**
     * code : 0
     * uplvexp : {"lv_exp_up":0,"lv":1,"ID":"1","lv_exp":1000}
     * member : {"address":null,"areaLocal":null,"brithday":null,"captcha":null,"city":null,"confirmPassword":null,"createDate":"2018-04-15 10:07:17","createId":"-1","distributorCode":"-1","email":null,"experValue":0,"hashs":null,"id":"da64b40a84af404ba2e3553e4615cb6e","identityCard":null,"ifMessage":null,"ifNote":null,"integralValue":0,"isAllowBuy":0,"isAllowReporting":0,"isAllowSpeech":0,"isLogin":0,"lastLogin":null,"level":"0","loginCount":null,"loginName":"15091776297","modifyDate":"2018-04-15 20:38:46","modifyId":null,"nickName":null,"password":null,"phone":"15091776297","picture":"https://membersbucket.oss-cn-huhehaote.aliyuncs.com/headinfo/150917762971523795919199.png","province":null,"qq":null,"sesameCredit":null,"sesameCreditDate":null,"sex":2,"shippingMethod":null,"spareTel":null,"status":0,"trueName":null,"type":null,"userId":"15091776297","vcode":null,"weibo":null}
     */

    private int code;
    private UplvexpBean uplvexp;
    private MemberBean member;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UplvexpBean getUplvexp() {
        return uplvexp;
    }

    public void setUplvexp(UplvexpBean uplvexp) {
        this.uplvexp = uplvexp;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public static class UplvexpBean {
        /**
         * lv_exp_up : 0
         * lv : 1
         * ID : 1
         * lv_exp : 1000
         */

        private int lv_exp_up;
        private int lv;
        private String ID;
        private int lv_exp;

        public int getLv_exp_up() {
            return lv_exp_up;
        }

        public void setLv_exp_up(int lv_exp_up) {
            this.lv_exp_up = lv_exp_up;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public int getLv_exp() {
            return lv_exp;
        }

        public void setLv_exp(int lv_exp) {
            this.lv_exp = lv_exp;
        }
    }

    public static class MemberBean {
        /**
         * address : null
         * areaLocal : null
         * brithday : null
         * captcha : null
         * city : null
         * confirmPassword : null
         * createDate : 2018-04-15 10:07:17
         * createId : -1
         * distributorCode : -1
         * email : null
         * experValue : 0
         * hashs : null
         * id : da64b40a84af404ba2e3553e4615cb6e
         * identityCard : null
         * ifMessage : null
         * ifNote : null
         * integralValue : 0
         * isAllowBuy : 0
         * isAllowReporting : 0
         * isAllowSpeech : 0
         * isLogin : 0
         * lastLogin : null
         * level : 0
         * loginCount : null
         * loginName : 15091776297
         * modifyDate : 2018-04-15 20:38:46
         * modifyId : null
         * nickName : null
         * password : null
         * phone : 15091776297
         * picture : https://membersbucket.oss-cn-huhehaote.aliyuncs.com/headinfo/150917762971523795919199.png
         * province : null
         * qq : null
         * sesameCredit : null
         * sesameCreditDate : null
         * sex : 2
         * shippingMethod : null
         * spareTel : null
         * status : 0
         * trueName : null
         * type : null
         * userId : 15091776297
         * vcode : null
         * weibo : null
         */

        private Object address;
        private Object areaLocal;
        private Object brithday;
        private Object captcha;
        private Object city;
        private Object confirmPassword;
        private String createDate;
        private String createId;
        private String distributorCode;
        private Object email;
        private int experValue;
        private Object hashs;
        private String id;
        private Object identityCard;
        private Object ifMessage;
        private Object ifNote;
        private int integralValue;
        private int isAllowBuy;
        private int isAllowReporting;
        private int isAllowSpeech;
        private int isLogin;
        private Object lastLogin;
        private String level;
        private Object loginCount;
        private String loginName;
        private String modifyDate;
        private Object modifyId;
        private Object nickName;
        private Object password;
        private String phone;
        private String picture;
        private Object province;
        private Object qq;
        private Object sesameCredit;
        private Object sesameCreditDate;
        private int sex;
        private Object shippingMethod;
        private Object spareTel;
        private int status;
        private Object trueName;
        private Object type;
        private String userId;
        private Object vcode;
        private Object weibo;

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getAreaLocal() {
            return areaLocal;
        }

        public void setAreaLocal(Object areaLocal) {
            this.areaLocal = areaLocal;
        }

        public Object getBrithday() {
            return brithday;
        }

        public void setBrithday(Object brithday) {
            this.brithday = brithday;
        }

        public Object getCaptcha() {
            return captcha;
        }

        public void setCaptcha(Object captcha) {
            this.captcha = captcha;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(Object confirmPassword) {
            this.confirmPassword = confirmPassword;
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

        public String getDistributorCode() {
            return distributorCode;
        }

        public void setDistributorCode(String distributorCode) {
            this.distributorCode = distributorCode;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public int getExperValue() {
            return experValue;
        }

        public void setExperValue(int experValue) {
            this.experValue = experValue;
        }

        public Object getHashs() {
            return hashs;
        }

        public void setHashs(Object hashs) {
            this.hashs = hashs;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(Object identityCard) {
            this.identityCard = identityCard;
        }

        public Object getIfMessage() {
            return ifMessage;
        }

        public void setIfMessage(Object ifMessage) {
            this.ifMessage = ifMessage;
        }

        public Object getIfNote() {
            return ifNote;
        }

        public void setIfNote(Object ifNote) {
            this.ifNote = ifNote;
        }

        public int getIntegralValue() {
            return integralValue;
        }

        public void setIntegralValue(int integralValue) {
            this.integralValue = integralValue;
        }

        public int getIsAllowBuy() {
            return isAllowBuy;
        }

        public void setIsAllowBuy(int isAllowBuy) {
            this.isAllowBuy = isAllowBuy;
        }

        public int getIsAllowReporting() {
            return isAllowReporting;
        }

        public void setIsAllowReporting(int isAllowReporting) {
            this.isAllowReporting = isAllowReporting;
        }

        public int getIsAllowSpeech() {
            return isAllowSpeech;
        }

        public void setIsAllowSpeech(int isAllowSpeech) {
            this.isAllowSpeech = isAllowSpeech;
        }

        public int getIsLogin() {
            return isLogin;
        }

        public void setIsLogin(int isLogin) {
            this.isLogin = isLogin;
        }

        public Object getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(Object lastLogin) {
            this.lastLogin = lastLogin;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Object getLoginCount() {
            return loginCount;
        }

        public void setLoginCount(Object loginCount) {
            this.loginCount = loginCount;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getModifyId() {
            return modifyId;
        }

        public void setModifyId(Object modifyId) {
            this.modifyId = modifyId;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getSesameCredit() {
            return sesameCredit;
        }

        public void setSesameCredit(Object sesameCredit) {
            this.sesameCredit = sesameCredit;
        }

        public Object getSesameCreditDate() {
            return sesameCreditDate;
        }

        public void setSesameCreditDate(Object sesameCreditDate) {
            this.sesameCreditDate = sesameCreditDate;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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

        public Object getTrueName() {
            return trueName;
        }

        public void setTrueName(Object trueName) {
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

        public Object getVcode() {
            return vcode;
        }

        public void setVcode(Object vcode) {
            this.vcode = vcode;
        }

        public Object getWeibo() {
            return weibo;
        }

        public void setWeibo(Object weibo) {
            this.weibo = weibo;
        }
    }
}
