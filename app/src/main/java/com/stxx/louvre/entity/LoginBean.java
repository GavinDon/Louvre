package com.stxx.louvre.entity;

/**
 * description: 登录
 * Created by liNan on 2017/8/10 16:54
 */

public class LoginBean {

    /**
     * flag : true
     * user : {"id":3,"sex":null,"username":"18602928514","address":null,"nickname":null,"age":null,"reserve":null,"openid":null,"realname":null,"reserve2":null,"password":"e10adc3949ba59abbe56e057f20f883e","reserve1":null}
     */

    private boolean flag;
    private UserBean user;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 3
         * sex : null
         * username : 18602928514
         * address : null
         * nickname : null
         * age : null
         * reserve : null
         * openid : null
         * realname : null
         * reserve2 : null
         * password : e10adc3949ba59abbe56e057f20f883e
         * reserve1 : null
         */

        private int id;
        private Object sex;
        private String username;
        private Object address;
        private Object nickname;
        private Object age;
        private Object reserve;
        private Object openid;
        private Object realname;
        private Object reserve2;
        private String password;
        private Object reserve1;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getReserve() {
            return reserve;
        }

        public void setReserve(Object reserve) {
            this.reserve = reserve;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getRealname() {
            return realname;
        }

        public void setRealname(Object realname) {
            this.realname = realname;
        }

        public Object getReserve2() {
            return reserve2;
        }

        public void setReserve2(Object reserve2) {
            this.reserve2 = reserve2;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getReserve1() {
            return reserve1;
        }

        public void setReserve1(Object reserve1) {
            this.reserve1 = reserve1;
        }
    }
}
