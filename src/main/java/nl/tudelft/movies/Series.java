package nl.tudelft.movies;

import java.util.List;
import java.util.Objects;

/**
 * A series: a media item with a number of episodes in addition to the common
 * attributes defined by {@link MediaItem}. The {@link Duration} of a series
 * represents the length of a single episode.
 */
public class Series extends MediaItem {

    private final int numberOfEpisodes;

    /**
     * Creates a series.
     *
     * @param title            the title of the series
     * @param genres           one or more genres
     * @param contentRating    the content rating
     * @param episodeDuration  the duration of a single episode
     * @param numberOfEpisodes the number of episodes
     * @param cast             one or more cast members
     */
    public Series(String title, List<String> genres, ContentRating contentRating,
                  Duration episodeDuration, int numberOfEpisodes,
                  List<CastMember> cast) {
        super(title, genres, contentRating, episodeDuration, cast);
        this.numberOfEpisodes = numberOfEpisodes;
    }

    /**
     * Returns the number of episodes of the series.
     *
     * @return the number of episodes
     */
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    @Override
    public String format() {
        return "Series: " + getTitle()
                + " - Genres: " + formatGenres()
                + ". Maturity rating: " + getContentRating().getLabel()
                + System.lineSeparator()
                + "Duration: " + getDuration().format() + ", "
                + numberOfEpisodes + " episodes."
                + System.lineSeparator()
                + "Cast members: " + formatCast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Series series = (Series) o;
        return numberOfEpisodes == series.numberOfEpisodes;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Integer.hashCode(numberOfEpisodes);
        return result;
    }

    @Override
    public String toString() {
        return "Series{title='" + getTitle() + "', numberOfEpisodes="
                + numberOfEpisodes + "}";
    }
}
