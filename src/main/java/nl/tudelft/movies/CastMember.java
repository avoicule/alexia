package nl.tudelft.movies;

import java.util.Objects;

/**
 * A single cast member of a media item, consisting of the person's name and the
 * role they play.
 */
public class CastMember {

    private final String name;
    private final String role;

    /**
     * Creates a cast member.
     *
     * @param name the name of the person (for example {@code "Margot Robbie"})
     * @param role the role the person plays (for example {@code "Barbie"})
     */
    public CastMember(String name, String role) {
        this.name = name;
        this.role = role;
    }
ss

    /**
     * Returns the name of the cast member.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the role the cast member plays.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Formats this cast member for display, for example {@code "Margot Robbie (Barbie)"}.
     *
     * @return a human-readable representation
     */
    public String format() {
        return name + " (" + role + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CastMember that = (CastMember) o;
        return Objects.equals(name, that.name) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(role);
        return result;
    }

    @Override
    public String toString() {
        return "CastMember{name='" + name + "', role='" + role + "'}";
    }
}
