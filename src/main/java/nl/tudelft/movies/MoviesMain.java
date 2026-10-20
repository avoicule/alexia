package nl.tudelft.movies;

import java.util.Scanner;

/**
 * Entry point for the Master Movies command line application.
 */
public final class MoviesMain {

    private MoviesMain() {
        // Entry-point class; not meant to be instantiated.
    }

    /**
     * Starts the application.
     *
     * <p>The input file name may be supplied as the first program argument. When
     * it is not, the application asks the user for it. The file name is never
     * hardcoded.</p>
     *
     * @param args the program arguments; {@code args[0]} is the optional file name
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new MasterMoviesApp(scanner).run(args);
        }
    }
}
