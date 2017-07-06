package com.knoxpo.personinfo;

import com.knoxpo.personinfo.model.Person;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class PersonTest {

    @BeforeClass
    public static void beforeClass(){
        System.out.println("Before class");
    }

    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void isGetAgeValid(){
        System.out.println("Test");
        Person p1 = new Person();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE,15);
        c.set(Calendar.MONTH,7);
        c.set(Calendar.YEAR,2000);
        p1.setDob(c.getTime());
        assertEquals("should be ",17,p1.getAge());
    }

    @After
    public void after(){
        System.out.println("After");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("After class");
    }
}
