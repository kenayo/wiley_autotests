package com.wiley.webdriver;

import com.wiley.webdriver.tests.MainTest;

import org.junit.runner.Result;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

public class WileyAutotest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MainTest.class);

        if (result.wasSuccessful()) {
            System.out.println("Test was successful.");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}