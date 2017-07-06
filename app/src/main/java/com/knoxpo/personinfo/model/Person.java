package com.knoxpo.personinfo.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class Person {

    private long id;
    private String mFname,mLname;
    private Date mDob;



    public String getFname() {
        return mFname;
    }

    public void setFname(String fname) {
        mFname = fname;
    }

    public String getLname() {
        return mLname;
    }

    public void setLname(String lname) {
        mLname = lname;
    }

    public Date getDob() {
        return mDob;
    }

    public void setDob(Date dob) {
        mDob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge(){
        Calendar c = Calendar.getInstance();
        c.setTime(mDob);
        return Calendar.getInstance().get(Calendar.YEAR) - c.get(Calendar.YEAR);
    }
}
