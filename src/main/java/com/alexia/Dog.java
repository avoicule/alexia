package com.alexia;

public class Dog extends AbstractAnimal {

    String name;
    Dog(){
        System.out.println("a dog was born!");

    }

    public void speak(){
        System.out.println("bark");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = "Cainele " + name;

    }
    @Override
    public String eat() {
        return "sdada";
    }

    @Override
    public String sleep(String hours) {
        return hours;
    }
}
