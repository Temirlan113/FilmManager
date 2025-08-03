import java.util.*;

public interface FilmManager {
    void addFilm(ArrayList<Film> filmList, HashMap<Integer, Film> filmMap, HashSet<String> filmNames, Scanner scanner);
    void deleteFilm(ArrayList<Film> filmList, HashMap<Integer, Film> filmMap, Scanner scanner);
    void searchFilm(ArrayList<Film> filmList, Scanner scanner);
    void sortFilms(ArrayList<Film> filmList);
    void showAllFilms(ArrayList<Film> filmList);
    void searchID(HashMap<Integer, Film> filmMap, Scanner scanner);
    void saveFilmsToFile(List<Film> filmList, String fileName);
    List<Film> loadFilmsFromFile(String fileName);
}
