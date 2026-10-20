package nl.tudelft.movies;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the media items read from the input file and the restrictions that are
 * currently active.
 *
 * <p>Restrictions (a genre filter and a content-rating filter) stack on top of
 * each other: calling {@link #matchingItems()} returns only the items that
 * satisfy all active restrictions. The original list of items is kept in
 * memory, so {@link #reset()} can restore the unfiltered view without having to
 * read the input file again.</p>
 */
public class Catalog {

    private final List<MediaItem> items;
    private String genreFilter;
    private ContentRating ratingFilter;

    /**
     * Creates a catalog for the given items.
     *
     * @param items the items read from the input file
     */
    public Catalog(List<MediaItem> items) {
        this.items = new ArrayList<>(items);
    }

    /**
     * Restricts the shown items to those that have the given genre.
     *
     * @param genre the genre to filter on (for example {@code "Crime"})
     */
    public void filterByGenre(String genre) {
        this.genreFilter = genre;
    }

    /**
     * Restricts the shown items to those with the given content rating or a less
     * restrictive one.
     *
     * @param rating the most restrictive content rating that should still be shown
     */
    public void filterByContentRating(ContentRating rating) {
        this.ratingFilter = rating;
    }

    /**
     * Resets all active restrictions, restoring the original list of items.
     * This does not re-read the input file.
     */
    public void reset() {
        this.genreFilter = null;
        this.ratingFilter = null;
    }

    /**
     * Returns the currently active genre filter, or {@code null} if none is set.
     *
     * @return the active genre filter or {@code null}
     */
    public String getGenreFilter() {
        return genreFilter;
    }

    /**
     * Returns the currently active content-rating filter, or {@code null} if
     * none is set.
     *
     * @return the active content-rating filter or {@code null}
     */
    public ContentRating getRatingFilter() {
        return ratingFilter;
    }

    /**
     * Returns the total number of items in the catalog, ignoring any filters.
     *
     * @return the number of items originally loaded
     */
    public int totalItemCount() {
        return items.size();
    }

    /**
     * Returns all items that satisfy every active restriction, in the order they
     * were loaded. When no restrictions are set, all items are returned.
     *
     * @return the matching items
     */
    public List<MediaItem> matchingItems() {
        List<MediaItem> result = new ArrayList<>();
        for (MediaItem item : items) {
            if (matches(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Indicates whether the given item satisfies all currently active
     * restrictions.
     *
     * @param item the item to test
     * @return {@code true} if the item passes every active restriction
     */
    private boolean matches(MediaItem item) {
        if (genreFilter != null && !item.hasGenre(genreFilter)) {
            return false;
        }
        if (ratingFilter != null
                && !item.getContentRating().isAllowedUpTo(ratingFilter)) {
            return false;
        }
        return true;
    }
}
