package nl.tudelft;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        List<Recipe> recipes = readFromFile("src/main/resources/recipes.txt");
        System.out.println(recipes);
    }

    public static List<Recipe> readFromFile(String filePath)
            throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filePath));
        List<Recipe> recipes = new ArrayList<>();

        while (scanner.hasNextLine()) {
            Recipe newRecipe = Recipe.read(scanner);
            recipes.add(newRecipe);
        }
        // If we get here "recipes" is full of recipes!
        return recipes;
    }
}

