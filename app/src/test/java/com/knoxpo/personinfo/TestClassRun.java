package com.knoxpo.personinfo;

import org.junit.runner.JUnitCore;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class TestClassRun {

    public static void main(String args[]){
        JUnitCore runner = new JUnitCore();
        runner.addListener(new OurListener());
        runner.run(PersonTest.class);
    }
}
