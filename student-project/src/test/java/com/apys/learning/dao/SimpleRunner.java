package com.apys.learning.dao;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleRunner {
    public static void main(String[] args) {
        SimpleRunner simpleRunner = new SimpleRunner();

        simpleRunner.runTests();
    }

    private void runTests() {
        try {
            Class<?> cl = Class.forName("com.apys.learning.dao.DictionaryDAOImplTest");

            Constructor constructor = cl.getConstructor();

            Object object = constructor.newInstance();
            Method[] methods = cl.getMethods();
            for(Method m : methods) {
                Test annotation = m.getAnnotation(Test.class);
                if(annotation != null) {
                    m.invoke(object);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
