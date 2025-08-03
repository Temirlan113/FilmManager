import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FilmDAO {
    public void addFilmToDB(Film film) {
        String sql = "INSERT INTO films (title, director, year) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, film.getFilm());
            stmt.setString(2, film.getDirector());
            stmt.setInt(3, film.getYear());

            stmt.executeUpdate();
            System.out.println("Фильм успешно добавлен в базу данных!");

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении фильма в БД");
            e.printStackTrace();
        }
    }

    public List<Film> getAllFilmsFromDB() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT id, title, director, year FROM films";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("director");
                int year = rs.getInt("year");

                Film film = new Film(title, director, year);

                film.setId(id);
                films.add(film);

            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении фильмов из БД");
            e.printStackTrace();
        }
        return films;
    }

    public void deleteFilmById(int id) {
        String sql = "DELETE FROM films WHERE id= ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Фильм с ID" + id + " успешно удален из БД");
            } else {
                System.out.println("Фильм с таким ID не найден");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении фильма из БД");
            e.printStackTrace();
        }

    }

    public Optional<Film> findFilmByTitle(String title) {
        String sql = "SELECT * FROM films WHERE title = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Film film = new Film(
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("year")
                );
                film.setId(rs.getInt("id"));
                return Optional.of(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Film> getAllSortedByYear() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM films ORDER BY year ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){
                Film film = new Film(
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("year")
                );

                film.setId(rs.getInt("id"));
                films.add(film);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return films;
    }


}

