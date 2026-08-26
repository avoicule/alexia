package com.alexia;

public class Cat extends Animal{
    Cat(){
        super(4, "black");
        System.out.println("a cat was born!");
    }
    Cat(int legs, String colour){
        super(legs, colour);
        System.out.println("a cat was born!");
    }

   // @Override
    public void speak(){
        System.out.println("meow");
    }

    @Override
    public String getName(String nume, String prenume) {
        return "Animal name " + nume + " | " + prenume;
    }
}
