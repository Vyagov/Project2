package backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor extends BaseEntity {
    private String pathToImage;
    private String name;
    private String born;
    private String biography;
    private Set<Movie> movies;

    public Actor() {
    }

    @Column(name = "path_to_image", nullable = false)
    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "born", nullable = false)
    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    @Column(name = "biography", columnDefinition = "TEXT", nullable = false)
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @ManyToMany(mappedBy = "actors", targetEntity = Movie.class)
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getCast().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getCast().remove(this);
    }
}
