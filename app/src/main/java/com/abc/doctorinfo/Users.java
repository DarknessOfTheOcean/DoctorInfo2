package com.abc.doctorinfo;

public class Users {



    public String name;
    public String email;
    public String city;
    public String mobile;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(com.abc.doctorinfo.Users.class)
    }

    public Users(String name, String email, String city,String mobile) {
        this.name = name;
        this.email = email;
        this.city=city;
        this.mobile=mobile;
    }

}
