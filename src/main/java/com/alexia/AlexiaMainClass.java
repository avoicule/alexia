package com.alexia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AlexiaMainClass {
    public static void main(String[] args) {


      /*  Cat[] cats = new Cat[3];*/

        Cat catTom = new Cat();
        catTom.setName("Tom");

        Cat catJerry = new Cat();
        catJerry.setName("Jerry");

        Dog dog = new Dog();
        dog.setName("Jimmy");

        List<AnimalInterface> catsList = new ArrayList<>();
        catsList.add(catTom);
        catsList.add(catJerry);
        catsList.add(dog);
        System.out.println(catsList);

        for (AnimalInterface animal : catsList) {
            System.out.println(animal.adopt());
            System.out.println(animal.getName());

        }
       /* List<AnimalInterface> animals = new ArrayList<>();
        animals.add(new Mouse());
        animals.add(new Dog());
        animals.add(new Cat());

        for (AnimalInterface animal : animals) {
            System.out.println(animal.eat());
            System.out.println(animal.sleep("8 hours"));
        }*/

    }
}
