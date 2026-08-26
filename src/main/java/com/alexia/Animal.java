package com.alexia;

public abstract class Animal {

     int legs = 0;
     String animalColour = "white";
    Animal() {
        this.legs=1000;
        this.animalColour="green";
        System.out.println("Animal created");
    }

    Animal(int legs, String colour) {
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

    public abstract String getName(String familyName, String lastName);
}
