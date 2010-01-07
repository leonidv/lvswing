package com.vygovskiy.controls.demo.model;

/**
 * Классический пример JavaBeans.
 * 
 * @author leonidv
 * 
 */
public class Person {
    private String firstName = "Leonid";
    private String lastName = "Vygovskiy";
    private int age = 23;
    private double rate = 1.25;
    private boolean married;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return String.format("firstName = %s, lastName = %s, age = %d, "
                + "rate = %f, married = %b", firstName, lastName, age, rate,
                married);
    }
}
