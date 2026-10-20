package nl.tudelft.movies;

/**
 * The content rating of a media item.
 *
 * <p>Content ratings have an ordering from least restrictive ({@link #G}) to
 * most restrictive ({@link #NC_17}). The order in which the constants are
 * declared defines this ordering, so {@link Enum#ordinal()} can be used to
 * compare restrictiveness.</p>
 */
public enum ContentRating {

    /** General Audiences: all ages admitted. */
    G("G", "General Audiences", "All ages admitted."),

    /** Parental Guidance Suggested: some material may not be suitable for children. */
    PG("PG", "Parental Guidance Suggested",
            "Some material may not be suitable for children."),

    /** Parents Strongly Cautioned: some material may be inappropriate for children under 13. */
    PG_13("PG-13", "Parents Strongly Cautioned",
            "Some material may be inappropriate for children under 13."),

    /** Restricted: under 17 requires accompanying parent or adult guardian. */
    R("R", "Restricted",
            "Under 17 requires accompanying parent or adult guardian."),

    /** Adults Only: no one 17 and under admitted. */
    NC_17("NC-17", "Adults Only",
            "No one 17 and under admitted. Clearly adult. Children are not admitted.");

    private final String label;
    private final String title;
    private final String explanation;

    /**
     * Creates a content rating.
     *
     * @param label       the textual label as used in the input file (for example {@code "PG-13"})
     * @param title       the short human-readable title of the rating
     * @param explanation a one-line explanation of what the rating means
     */
    ContentRating(String label, String title, String explanation) {
        this.label = label;
        this.title = title;
        this.explanation = explanation;
    }

    /**
     * Returns the textual label of this rating as used in the input file.
     *
     * @return the label, for example {@code "PG-13"}
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the short human-readable title of this rating.
     *
     * @return the title, for example {@code "Parents Strongly Cautioned"}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the explanation of this rating.
     *
     * @return the explanation line
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Indicates whether this rating is allowed when the given maximum
     * (least/most restrictive threshold) is selected by the user.
     *
     * <p>When a user selects a rating, items with that rating or any less
     * restrictive rating are allowed, while more restrictive items are filtered
     * out.</p>
     *
     * @param selected the rating selected by the user as the most restrictive allowed
     * @return {@code true} if this rating is at most as restrictive as {@code selected}
     */
    public boolean isAllowedUpTo(ContentRating selected) {
        return this.ordinal() <= selected.ordinal();
    }

    /**
     * Resolves a {@link ContentRating} from its textual label.
     *
     * @param label the label to parse, for example {@code "PG-13"} (case-insensitive)
     * @return the matching content rating
     * @throws IllegalArgumentException if no rating matches the given label
     */
    public static ContentRating fromLabel(String label) {
        String normalised = label.trim();
        for (ContentRating rating : values()) {
            if (rating.label.equalsIgnoreCase(normalised)) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Unknown content rating: " + label);
    }
}
