import java.io.Serializable;

public class Film implements Serializable {
    private static final long serialVersionUID = 1L; //Для сериализации
    String film;
    String director;
    int year;
    private int id;
    private static int idCounter = 1;

    public Film(String film, String director, int year) {
        this.film = film;
        this.director = director;
        this.year = year;
        this.id = idCounter++;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Фильм: " + film + "\nРежиссер: " + director + " \nГод выхода: " + year;
    }

    public int getYear() {
        return year;
    }

    public String getFilm() {
        return film;
    }

    public int getId() {
        return id;
    }

    public String getDirector() {
        return director;
    }

    public void setId(int id) {
        this.id = id;
    }


}
