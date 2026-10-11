package nl.tudelft;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Recipe {

    private final String name;
    private final List<Ingredient> ingredients;
    private final String instructions;

    public Recipe(String name, List<Ingredient> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public static Recipe read(Scanner scanner) {
        String name = scanner.nextLine();
        String ingredientString = scanner.nextLine();
        String instructions = scanner.nextLine();

        Scanner ingredientScanner = new Scanner(ingredientString);
        ingredientScanner.useDelimiter(", ");
        List<Ingredient> ingredients = new ArrayList<>();

        while(ingredientScanner.hasNext()){
            ingredients.add(Ingredient.read(ingredientScanner.next()));
        }
        return new Recipe(name, ingredients, instructions);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(instructions, recipe.instructions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(ingredients);
        result = 31 * result + Objects.hashCode(instructions);
        return result;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
