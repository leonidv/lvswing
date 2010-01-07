package com.vygovskiy.beansutils;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.vygovskiy.controls.beansutils.BeanPropertiesInfo;
import com.vygovskiy.controls.demo.model.Person;


public class BeanPropertyInfoTest {

    @Test
    public void testGetInfoForBean() throws IntrospectionException {
        Person person = new Person();

        BeanPropertiesInfo<Person> data = new BeanPropertiesInfo<Person>(person);

        assertSame(6, data.size());

        assertTrue(data.contains("lastName"));
        assertTrue(data.contains("firstName"));
        assertTrue(data.contains("age"));
    }

}
