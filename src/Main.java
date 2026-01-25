import edu.aitu.oop3.models.*;
import edu.aitu.oop3.patterns.GymConfig;
import edu.aitu.oop3.patterns.MembershipFactory;
import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.services.AdminService;
import edu.aitu.oop3.services.BookingService;
import edu.aitu.oop3.util.GenericFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClassRepository classRepo = new PostgresClassRepository();
    private static final BookingService bookingService = new BookingService(
            new PostgresMemberRepository(),
            classRepo,
            new PostgresBookingRepository(),
            (m, c) -> System.out.println("[Email] Отправлено уведомление " + m.getName() + " о записи на " + c.getTitle())
    );
    private static final AdminService adminService = new AdminService(classRepo);

    public static void main(String[] args) {
        GymConfig config = GymConfig.getInstance();
        System.out.println("=== Фитнес-центр: " + config.getDbUrl() + " ===");

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Забронировать занятие");
            System.out.println("2. Создать план тренировок");
            System.out.println("3. Оформить абонемент");
            System.out.println("4. Поиск занятий (фильтр)");
            System.out.println("5. Посмотреть все занятия");
            System.out.println("6. Панель администратора");
            System.out.println("0. Выход");
            System.out.print("> ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleBooking();
                    break;
                case "2":
                    handleTrainingPlan();
                    break;
                case "3":
                    handleMembership();
                    break;
                case "4":
                    handleFiltering();
                    break;
                case "5":
                    handleShowAllClasses();
                    break;
                case "6":
                    handleAdminPanel();
                    break;
                case "0":
                    running = false;
                    System.out.println("Завершение работы...");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }

    private static void handleBooking() {
        try {
            System.out.println("--- Бронирование занятия ---");
            System.out.print("Введите ID участника: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            System.out.print("Введите ID занятия: ");
            int classId = Integer.parseInt(scanner.nextLine());

            bookingService.bookClass(memberId, classId);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        } catch (Exception e) {
            System.out.println("Ошибка бронирования: " + e.getMessage());
        }
    }

    private static void handleTrainingPlan() {
        System.out.println("--- Создание плана тренировок ---");
        System.out.print("Введите цель (напр., Muscle Gain): ");
        String goal = scanner.nextLine();
        System.out.print("Кол-во дней в неделю: ");
        int days = Integer.parseInt(scanner.nextLine());
        System.out.print("Имя тренера: ");
        String trainer = scanner.nextLine();

        TrainingPlan plan = new TrainingPlan.Builder()
                .setGoal(goal)
                .setDaysPerWeek(days)
                .setTrainer(trainer)
                .build();
        System.out.println("План создан: " + plan);
    }

    private static void handleMembership() {
        System.out.println("--- Оформление абонемента ---");
        System.out.print("Введите тип (MONTHLY/YEARLY): ");
        String type = scanner.nextLine();
        try {
            Membership membership = MembershipFactory.createMembership(type);
            System.out.println("Создан абонемент: " + membership.getTypeName() + ", Цена: " + membership.getBasePrice());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void handleFiltering() {
        System.out.println("--- Поиск занятий ---");
        List<FitnessClass> classes = classRepo.findAll();

        System.out.print("Введите ключевое слово для поиска (напр., Yoga): ");
        String query = scanner.nextLine();

        GenericFilter<FitnessClass> filter = new GenericFilter<>();
        List<FitnessClass> results = filter.filter(classes, c -> c.getTitle().toLowerCase().contains(query.toLowerCase()));

        System.out.println("Результаты поиска (" + results.size() + "):");
        results.forEach(c -> System.out.println(" - " + c.getTitle() + " (Инструктор: " + c.getInstructorName() + ")"));
    }

    private static void handleAdminPanel() {
        System.out.println("--- Панель администратора ---");
        System.out.print("Введите пароль администратора: ");
        String password = scanner.nextLine();

        if (GymConfig.getInstance().getAdminPassword().equals(password)) {
            boolean adminRunning = true;
            while (adminRunning) {
                System.out.println("\nМеню администратора:");
                System.out.println("1. Создать новое занятие");
                System.out.println("2. Удалить занятие");
                System.out.println("0. Вернуться в главное меню");
                System.out.print("> ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        handleAddClass();
                        break;
                    case "2":
                        handleDeleteClass();
                        break;
                    case "0":
                        adminRunning = false;
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                }
            }
        } else {
            System.out.println("Ошибка: Неверный пароль!");
        }
    }

    private static void handleAddClass() {
        try {
            System.out.println("--- Создание нового занятия ---");
            System.out.print("Введите название занятия: ");
            String title = scanner.nextLine();
            System.out.print("Введите имя тренера: ");
            String instructor = scanner.nextLine();
            System.out.print("Введите максимальное кол-во мест: ");
            int capacity = Integer.parseInt(scanner.nextLine());
            System.out.print("Введите время (ГГГГ-ММ-ДДTЧЧ:ММ): ");
            String timeStr = scanner.nextLine();
            LocalDateTime time = LocalDateTime.parse(timeStr);

            adminService.addClass(title, instructor, capacity, time);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неверный формат времени. Используйте ISO формат (напр., 2026-01-20T10:00).");
        }
    }

    private static void handleShowAllClasses() {
        System.out.println("--- Список всех занятий ---");
        List<FitnessClass> classes = classRepo.findAll();
        if (classes.isEmpty()) {
            System.out.println("Занятий пока нет.");
        } else {
            classes.forEach(c -> System.out.println("ID: " + c.getId() + " | " + c.getTitle() + " | Тренер: " + c.getInstructorName() + " | Места: " + c.getMaxCapacity() + " | Время: " + c.getScheduleTime()));
        }
    }

    private static void handleDeleteClass() {
        try {
            System.out.println("--- Удаление занятия ---");
            System.out.print("Введите ID занятия для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());
            adminService.removeClass(id);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Введите корректное число.");
        }
    }
}
