package com.mirea.kt.ribo;

public class Password {

    private Integer id;
    private String username;
    private String password;
    private String website;
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Password(Integer id, String username, String password, String website, String category) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.website = website;
        this.category = category;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
