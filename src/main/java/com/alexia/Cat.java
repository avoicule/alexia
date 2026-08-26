package com.alexia;

public  class Cat extends AbstractAnimal {
    static String address="homeless";

    String name;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = "Pisica "+name;

    }


    @Override
    public String eat() {
        return "cat eats ";
    }

    @Override
    public String sleep(String hours) {
        return "cat sleeps " + hours;
    }
}
