package nl.tudelft.movies;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Command line interface for the Master Movies application.
 *
 * <p>This class reads user input from {@code System.in} and writes to
 * {@code System.out}. Because it interacts directly with the user and the file
 * system, its methods do not have to be unit tested; the actual logic lives in
 * the testable {@link Catalog}, {@link ContentRating} and {@link MediaFileLoader}
 * classes.</p>
 */
public class MasterMoviesApp {

    private final Scanner input;
    private Catalog catalog;

    /**
     * Creates the application with the given input source.
     *
     * @param input the scanner used to read user input
     */
    public MasterMoviesApp(Scanner input) {
        this.input = input;
    }

    /**
     * Starts the application: resolves the input file, loads the catalog and
     * then runs the interactive menu until the user chooses to quit.
     *
     * @param args the program arguments; the first argument, if present, is used
     *             as the input file name
     */
    public void run(String[] args) {
        String fileName = resolveFileName(args);
        try {
            List<MediaItem> items = MediaFileLoader.load(fileName);
            this.catalog = new Catalog(items);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + fileName);
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Could not read the file: " + e.getMessage());
            return;
        }
        menuLoop();
    }

    /**
     * Determines the input file name from the program arguments or, if none was
     * given, by asking the user.
     *
     * @param args the program arguments
     * @return the file name to read
     */
    private String resolveFileName(String[] args) {
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            return args[0];
        }
        System.out.print("Please enter the path to the input file: ");
        return input.nextLine().trim();
    }

    /**
     * Runs the interactive menu until the user chooses to quit.
     */
    private void menuLoop() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1" -> showItems();
                case "2" -> filterByGenre();
                case "3" -> filterByContentRating();
                case "4" -> resetCriteria();
                case "5" -> running = false;
                default -> System.out.println("Unknown choice, please try again.");
            }
            System.out.println();
        }
        System.out.println("Goodbye!");
    }

    /**
     * Prints the main menu.
     */
    private void printMenu() {
        System.out.println("Please make your choice:");
        System.out.println("1 - Show all items that meet all criteria.");
        System.out.println("2 - Filter items by genre.");
        System.out.println("3 - Filter items by content rating.");
        System.out.println("4 - Reset all criteria.");
        System.out.println("5 - Quit the application.");
    }

    /**
     * Option 1: shows all items that satisfy the active restrictions, or a clear
     * message when there are none.
     */
    private void showItems() {
        List<MediaItem> matching = catalog.matchingItems();
        if (matching.isEmpty()) {
            if (catalog.totalItemCount() == 0) {
                System.out.println("There are no items to show.");
            } else {
                System.out.println("No items meet the selected criteria.");
            }
            return;
        }
        for (MediaItem item : matching) {
            System.out.println(item.format());
        }
    }

    /**
     * Option 2: asks the user for a genre and stores it as a filter.
     */
    private void filterByGenre() {
        System.out.print("Enter a genre to filter by: ");
        String genre = input.nextLine().trim();
        catalog.filterByGenre(genre);
        System.out.println("Filtering on genre: " + genre);
    }

    /**
     * Option 3: shows the available content ratings with their explanations,
     * asks the user to choose one and stores it as a filter.
     */
    private void filterByContentRating() {
        System.out.println("Available content ratings:");
        for (ContentRating rating : ContentRating.values()) {
            System.out.println("  " + rating.getLabel() + ": " + rating.getTitle());
            System.out.println("      " + rating.getExplanation());
        }
        System.out.print("Enter a content rating (for example PG-13): ");
        String label = input.nextLine().trim();
        try {
            ContentRating rating = ContentRating.fromLabel(label);
            catalog.filterByContentRating(rating);
            System.out.println("Filtering on content rating up to: "
                    + rating.getLabel());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown content rating: " + label);
        }
    }

    /**
     * Option 4: resets all restrictions without re-reading the input file.
     */
    private void resetCriteria() {
        catalog.reset();
        System.out.println("All criteria have been reset.");
    }
}
