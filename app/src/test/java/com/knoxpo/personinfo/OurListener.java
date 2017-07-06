package com.knoxpo.personinfo;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Created by Tejas Sherdiwala on 4/11/2017.
 * &copy; Knoxpo
 */

public class OurListener extends RunListener {

    public void testRunStarted(Description description) throws Exception{
        System.out.println("Test cases to execute : " + description.testCount());
    }

    public void testRunFinished(Result result) throws Exception{
        System.out.println("Test cases executed : " + result.getRunCount());
    }

    public void testStarted(Description description) throws Exception{
        System.out.println("Execution Started : " + description.getMethodName());
    }

    public void testFinished(Description description) throws Exception{
        System.out.println("Execution Finished : " + description.getMethodName());
    }

    public void testFailure(Failure failure) throws Exception{
        System.out.println("Execution Fail : " + failure.getException());
    }

    public void testIgnored(Description description) throws Exception{
        System.out.println("Test Ignored : " + description.getMethodName());
    }

    public void testAssumptionFailure(Failure failure){
        System.out.println("Assumption failure : " + failure.getMessage());
    }
}
