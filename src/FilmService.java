import java.io.*;
import java.util.*;

public class FilmService implements FilmManager {
    private final FilmDAO filmDAO;

    public FilmService(){
        this.filmDAO = new FilmDAO();
    }

    @Override
    //Добавить фильм
    public void addFilm(ArrayList<Film> filmList, HashMap<Integer, Film> filmMap, HashSet<String> filmNames, Scanner scanner) {

        System.out.println("Введите фильм: ");
        String userFilm = scanner.nextLine();
        if (filmNames.contains(userFilm)) {
            System.out.println("Фильм с таким названием уже существует");
            return;
        }

        System.out.println("Введите режиссера: ");
        String userDirector = scanner.nextLine();
        System.out.println("Введите год выхода: ");
        int userYear = scanner.nextInt();
        scanner.nextLine();


        Film addUserFilm = new Film(userFilm, userDirector, userYear);
        filmList.add(addUserFilm);
        filmMap.put(addUserFilm.getId(), addUserFilm);
        filmNames.add(addUserFilm.getFilm());

        FilmDAO filmDAO = new FilmDAO();
        filmDAO.addFilmToDB(addUserFilm);

        System.out.println("Фильм добавлен");
    }


    @Override
    //Удалить фильм
    public void deleteFilm(ArrayList<Film> filmList, HashMap<Integer, Film> filmMap, Scanner scanner) {

        if (filmList.isEmpty()) {
            System.out.println("Фильмов нету");
        } else {
            System.out.println("Список фильмов:");
            for (int i = 0; i < filmList.size(); i++) {
                System.out.println((i + 1) + ". " + filmList.get(i));
            }

            System.out.println("Укажите номер фильма, который хотите удалить: ");
            int filmRemove = scanner.nextInt();

            if (filmRemove >= 1 && filmRemove <= filmList.size()) {
                Film removedFilm = filmList.remove(filmRemove - 1);
                filmMap.remove(removedFilm.getId());
                System.out.println("Фильм удалён");
            } else {
                System.out.println("Неверный номер. Попробуйте снова.");
            }
        }
    }

    public void showSortedFilms() {
        List<Film> sortedFilms = filmDAO.getAllSortedByYear();
        if (sortedFilms.isEmpty()) {
            System.out.println("Фильмы не найдены");
        } else {
            sortedFilms.forEach(System.out::println);
        }
    }

    public void searchFilmByTitle(Scanner scanner) {
        System.out.println("Введите название фильма:");
        String title = scanner.nextLine();

        Optional<Film> film = filmDAO.findFilmByTitle(title);
        film.ifPresentOrElse(
                f -> System.out.println("Найден фильм:\n" + f),
                () -> System.out.println("Фильм не найден")
        );
    }

    @Override
    //Поиск фильма
    public void searchFilm(ArrayList<Film> filmList, Scanner scanner) {
        System.out.println("Введите название фильма, который хотите найти: ");
        String inputSearch = scanner.nextLine();
        filmList.stream()
                .filter(film -> film.getFilm().equalsIgnoreCase(inputSearch))
                .findFirst()
                .ifPresentOrElse(
                        film -> System.out.println("Фильм найден" + film),
                        () -> System.out.println("Фильм не найден")
                );
    }

    @Override
    //Сортировка фильмов по возрастанию и году
    public void sortFilms(ArrayList<Film> filmList) {


        if (filmList.isEmpty()) {
            System.out.println("Фильмы отсутствуют, сортировка невозможна");
        } else {
            System.out.println("Список фильмов отсортирован по году: ");
            filmList.stream()
                    .sorted(Comparator.comparing(Film::getYear))
                    .forEach(System.out::println);
        }
    }

    @Override
    //Показать все фильмы
    public void showAllFilms(ArrayList<Film> filmList) {
        if (!filmList.isEmpty()) {
            for (Film film : filmList) {
                System.out.println(film);
            }
        } else {
            System.out.println("Список пуст");
        }
    }

    @Override
    //Поиск по ID
    public void searchID(HashMap<Integer, Film> filmMap, Scanner scanner) {
        System.out.println("Введите ID фильма:");
        int idToSearch = scanner.nextInt();
        scanner.nextLine();


        Optional<Film> optionalFilm = Optional.ofNullable(filmMap.get(idToSearch));

        optionalFilm.ifPresentOrElse(
                film -> System.out.println("Фильм найден: " + film),
                () -> System.out.println("Фильм с таким ID не найден. ")
        );
    }

    public void saveFilmsToFile(List<Film> filmList, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(filmList);
            System.out.println("Фильмы успешно сохранены в файл");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public List<Film> loadFilmsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Film>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке" + e.getMessage());
        }
        return new ArrayList<>();
    }
}

