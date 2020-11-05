package com.ayprojects.helpinghands.models;

public class User extends CommonUsedAttributes{
    private long user_id;
    private String user_name;
    private Contact contact;
    private String password_hash;
    private String profile_img_path;
    private Address address;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getProfile_img_path() {
        return profile_img_path;
    }

    public void setProfile_img_path(String profile_img_path) {
        this.profile_img_path = profile_img_path;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User(double schemaVersion,String createdDateTime,String modifiedDateTime,String status,long user_id, String user_name, Contact contact, String password_hash, String profile_img_path, Address address) {
        this.schema_version=schemaVersion;
        this.created_date_time = createdDateTime;
        this.modified_date_time=modifiedDateTime;
        this.status=status;
        this.user_id = user_id;
        this.user_name = user_name;
        this.contact = contact;
        this.password_hash = password_hash;
        this.profile_img_path = profile_img_path;
        this.address = address;
    }
}
