/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.daza.app.model;

import java.util.List;

public class User extends Model {

    private String username;            // 用户名
    private String email;               // 邮箱
    private String mobile;              // 手机
    private String password;            // 密码
    private String name;                // 名称
    private String first_name;          // 名
    private String last_name;           // 姓
    private String real_name;           // 真实姓名
    private String avatar_url;          // 头像链接（原始尺寸）
    private boolean use_gravatar;       // 使用Gravatar头像
    private int age;                    // 年龄
    private String gender;              // 性别 = ['unspecified', 'secrecy', 'male', 'female']
    private String birthday;            // 生日
    private String country;             // 国家
    private String city;                // 城市
    private String address;             // 地址
    private String phone;               // 电话
    private String company;             // 公司
    private String website;             // 主页
    private String bio;                 // 简介
    private int status;                 // 状态[ 0 => 'Unactive', 1 => 'Active']
    private boolean site_admin;         // 站点管理员
    private int followers_count;        // 粉丝数
    private int following_count;        // 关注数
    private int topic_count;            // 主题数
    private int article_count;          // 文章数
    private List<UserConfig> configs;   //
    private AccessToken jwt_token;      //

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isUse_gravatar() {
        return use_gravatar;
    }

    public void setUse_gravatar(boolean use_gravatar) {
        this.use_gravatar = use_gravatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getTopic_count() {
        return topic_count;
    }

    public void setTopic_count(int topic_count) {
        this.topic_count = topic_count;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public List<UserConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<UserConfig> configs) {
        this.configs = configs;
    }

    public AccessToken getJwt_token() {
        return jwt_token;
    }

    public void setJwt_token(AccessToken jwt_token) {
        this.jwt_token = jwt_token;
    }
}
