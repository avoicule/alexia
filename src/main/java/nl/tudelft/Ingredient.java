package nl.tudelft;

import java.util.Objects;

public class Ingredient {
    private String name;
    private double quantity;
    private String unit;

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public static Ingredient read(String ingredient) {
        String[] parts = ingredient.split(" ", 2);
        double quantity;
        String unit = "";

        if(parts[0].contains("gr") || parts[0].contains("ml")) {
            String quantityString = parts[0].substring(0, parts[0].length() -2);
            unit = parts[0].substring(parts[0].length() -2);
            quantity = Double.parseDouble(quantityString);
        } else {
            quantity = Double.parseDouble(parts[0]);
        }
        return new Ingredient(parts[1], quantity, unit);
    }



    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;
        return Double.compare(quantity, that.quantity) == 0 && Objects.equals(name, that.name) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Double.hashCode(quantity);
        result = 31 * result + Objects.hashCode(unit);
        return result;
    }
}
