package com.alexia;

public class Dog extends Animal{
    Dog(){
        System.out.println("a dog was born!");

    }

    public void speak(){
        System.out.println("bark");
    }

    @Override
    public String getName(String firsName, String lastName  ) {
        return firsName + " | " + lastName;
    }
}
