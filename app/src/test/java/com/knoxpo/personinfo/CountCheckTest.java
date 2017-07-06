package com.knoxpo.personinfo;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.knoxpo.personinfo.model.CountCheck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

@RunWith(ConcurrentTestRunner.class)
public class CountCheckTest {

    private CountCheck counter = new CountCheck();

    @Before
    public void initCount(){
        counter.init(2);
    }

    @Test
    public void increment(){
        counter.addOne();
    }

    @After
    public void countTest(){
        assertEquals("should be 6",6,counter.getCount());
    }
}
