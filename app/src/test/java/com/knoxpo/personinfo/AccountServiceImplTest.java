package com.knoxpo.personinfo;

import com.knoxpo.personinfo.model.AccountServiceImpl;
import com.knoxpo.personinfo.model.Person;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class AccountServiceImplTest {
    AccountServiceImpl mAccountService = new AccountServiceImpl();

    @Test
    public void testCreateNew(){
        Person p = new Person();
        p.setFname("pinal");
        Person newAcc = mAccountService.createNew(p);
        assertThat(newAcc, isA(Person.class));
        assertEquals(newAcc.getFname() , p.getFname());
    }

    @Ignore
    @Test
    public void testUpdatePersonInfo(){
        Person p = new Person();
        p.setFname("xyx");
        //Person p1 = new Person();
        Person oldAcc = mAccountService.updatePersonInfo(p);
        assertNotEquals(oldAcc.getFname(),p.getFname());

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
        assertEquals(17,p1.getAge());
    }

}
