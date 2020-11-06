package com.ayprojects.helpinghands.models;

public class User extends CommonUsedAttributes{
    private String user_id;
    private String name;
    private String password;
    private EmbededImage profile_img;
    private Address address;
    private String mobile;
    private String email;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmbededImage getProfileImg() {
        return profile_img;
    }

    public void setProfile_img_path(EmbededImage profileImg) {
        this.profile_img = profileImg;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String user_id, String name,String password, EmbededImage embededImage, Address address,String mobileNumber,String emailId) {
        this.schema_version=schemaVersion;
        this.created_date_time = createdDateTime;
        this.modified_date_time=modifiedDateTime;
        this.status=status;
        this.user_id = user_id;
        this.name = name;
        this.mobile = mobileNumber;
        this.email = emailId;
        this.password = password;
        this.profile_img = embededImage;
        this.address = address;
    }
}
