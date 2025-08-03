import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()){
            System.out.println("Успешно подключено");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FilmService service = new FilmService();
        FilmService filmService = new FilmService();


        Scanner scanner = new Scanner(System.in);

        HashSet<String> filmNames = new HashSet<>();

        HashMap<Integer, Film> filmMap = new HashMap<>();

        ArrayList<Film> filmList = new ArrayList<>();
        Film film1 = new Film("Начало", "Кристофер Нолан", 2010);
        Film film2 = new Film("Интерстеллар", "Кристофер Нолан", 2014);
        Film film3 = new Film("Крестный Отец", "Фрэнсис Форд Коппола", 1972);
        Film film4 = new Film("Джон Уик", "Чад Стахелски", 2014);


        filmList.add(film1);
        filmList.add(film2);
        filmList.add(film3);
        filmList.add(film4);

        filmMap.put(film1.getId(), film1);
        filmMap.put(film2.getId(), film2);
        filmMap.put(film3.getId(), film3);
        filmMap.put(film4.getId(), film4);


        while (true) {
            try {


                System.out.println("Выберите пункт меню: ");
                System.out.println("1. Добавить фильм");
                System.out.println("2. Удалить фильм");
                System.out.println("3. Поиск фильма");
                System.out.println("4. Сортировать фильм по возрастанию");
                System.out.println("5. Показать все фильмы");
                System.out.println("6. Поиск по ID");
                System.out.println("7. Сохранить фильмы на диск");
                System.out.println("8. Загрузить фильмы с диска");
                int menu = scanner.nextInt();
                if (menu == 0) break;
                scanner.nextLine();


                switch (menu) {
                    case 1:
                        service.addFilm(filmList, filmMap, filmNames, scanner);
                        break;

                    case 2:
                        deleteFilmFromDB(scanner);
                        break;

                    case 3:
                        filmService.searchFilmByTitle(scanner);
                        break;
                    case 4:
                        filmService.showSortedFilms();
                        break;
                    case 5:
                        showAllFilmsFromDB();
                        break;

                    case 6:
                        service.searchID(filmMap, scanner);
                        break;

                    case 7:
                        service.saveFilmsToFile(filmList, "films.dat");
                        break;

                    case 8:
                        filmList.clear();
                        filmList.addAll(service.loadFilmsFromFile("films.dat"));
                        filmMap.clear();
                        filmNames.clear();
                        for (Film f : filmList) {
                            filmMap.put(f.getId(), f);
                            filmNames.add(f.getFilm());
                        }
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
                scanner.nextLine();
            }
        }

    }

    public static void showAllFilmsFromDB() {
        FilmDAO filmDAO = new FilmDAO();
        List<Film> filmsFromDB = filmDAO.getAllFilmsFromDB();

        if (filmsFromDB.isEmpty()){
            System.out.println("Список фильмов в базе пуст");
        } else {
            for (Film film: filmsFromDB){
                System.out.println(film);
            }
        }
    }

    public static void deleteFilmFromDB(Scanner scanner) {
        System.out.println("Введите ID фильма, который хотите удалить: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();

        FilmDAO filmDAO = new FilmDAO();
        filmDAO.deleteFilmById(idToDelete);
    }
}




