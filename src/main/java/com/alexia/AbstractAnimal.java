package com.alexia;

public abstract class AbstractAnimal implements AnimalInterface {

     int legs = 0;
     static final String owner="NoName";
     String animalColour = "white";
     String name;
    AbstractAnimal() {
        this.legs=1000;
        this.animalColour="green";
        System.out.println("Animal created");
    }

    AbstractAnimal(int legs, String colour) {
        this.legs = legs;
        animalColour = colour;
        System.out.println("Animal created with " + legs + " and " + colour);
    }



    public void speak() {
        System.out.println("Animal speaks");
    }
    public void speak(String language) {
        System.out.println("Animal speaks " + language);
    }

    public int getLegs() {
        return legs;
    }
    public String getColour() {
        return animalColour;
    }

    public boolean adopt(){
        System.out.println("adopted");
        return false;
    }
}
