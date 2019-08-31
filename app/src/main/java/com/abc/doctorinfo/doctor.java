package com.abc.doctorinfo;

public class doctor {


    private int id;
    private String name;
    private String speciality;
    private double rating;
    private String email ;
    private int image;
    private String location ;
    private String timings ;
    private String clinic ;
    private String phone ;




    public doctor(int id, String name,String speciality,double rating ,String email,int image,String location ,String timings,String clinic,String phone)

    {
        this.id=id;
        this.name=name;
        this.speciality=speciality;
        this.location=location;
        this.rating=rating;
        this.email=email;
        this.image=image;
        this.clinic=clinic;
        this.timings=timings;
        this.phone=phone;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    public String getSpeciality()
    {
        return speciality;
    }

    public String getLocation()
    {
        return location;
    }
    public double getRating()
    {
        return rating;
    }
    public String getEmail()
    {
        return email;
    }
    public int getImage()
    {
        return image;
    }
    public String getTimings()
    {
        return timings;
    }
    public String getClinic()
    {
        return clinic;
    }
    public String getPhoneno()
    {
        return phone;
    }


}
