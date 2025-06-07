package databazaapp;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseHandler dbHandler = new DatabaseHandler();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Выберите действие: ");
            switch (choice) {
                case 1 -> addTask();
                case 2 -> showAllTasks();
                case 3 -> showTaskById();
                case 4 -> updateTask();
                case 5 -> deleteTask();
                case 6 -> {
                    System.out.println("Выход из программы.");
                    running = false;
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Показать задачу по ID");
        System.out.println("4. Обновить задачу");
        System.out.println("5. Удалить задачу");
        System.out.println("6. Выход");
    }

    private static void addTask() {
        System.out.println("\nДобавление новой задачи:");
        String title;
        do {
            title = readLine("Введите название задачи (не пустое): ");
            if (title.isBlank()) {
                System.out.println("Название не может быть пустым.");
            }
        } while (title.isBlank());

        String description = readLine("Введите описание задачи: ");

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus("NEW");

        dbHandler.addTask(task);
        System.out.println("Задача успешно добавлена.");
    }

    private static void showAllTasks() {
        System.out.println("\nСписок всех задач:");
        List<Task> tasks = dbHandler.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задачи отсутствуют.");
        } else {
            for (Task task : tasks) {
                System.out.printf("ID: %d | Название: %s | Статус: %s | Создана: %s%n",
                        task.getId(), task.getTitle(), task.getStatus(), task.getCreatedAt());
            }
        }
    }

    private static void showTaskById() {
        int id = readInt("Введите ID задачи для просмотра: ");
        Task task = dbHandler.getTaskById(id);
        if (task == null) {
            System.out.println("Задача с таким ID не найдена.");
        } else {
            System.out.println("\nДетали задачи:");
            System.out.println("ID: " + task.getId());
            System.out.println("Название: " + task.getTitle());
            System.out.println("Описание: " + task.getDescription());
            System.out.println("Статус: " + task.getStatus());
            System.out.println("Дата создания: " + task.getCreatedAt());
        }
    }

    private static void updateTask() {
        int id = readInt("Введите ID задачи для обновления: ");
        Task task = dbHandler.getTaskById(id);
        if (task == null) {
            System.out.println("Задача с таким ID не найдена.");
            return;
        }

        System.out.println("Текущие данные задачи:");
        System.out.println("Название: " + task.getTitle());
        System.out.println("Описание: " + task.getDescription());
        System.out.println("Статус: " + task.getStatus());

        String title;
        do {
            title = readLine("Введите новое название задачи (не пустое): ");
            if (title.isBlank()) {
                System.out.println("Название не может быть пустым.");
            }
        } while (title.isBlank());

        String description = readLine("Введите новое описание задачи: ");

        String status;
        do {
            status = readLine("Введите статус (NEW, IN_PROGRESS, DONE): ").toUpperCase();
            if (!status.equals("NEW") && !status.equals("IN_PROGRESS") && !status.equals("DONE")) {
                System.out.println("Некорректный статус. Попробуйте снова.");
            }
        } while (!status.equals("NEW") && !status.equals("IN_PROGRESS") && !status.equals("DONE"));
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);

        boolean updated = dbHandler.updateTask(task);
        if (updated) {
            System.out.println("Задача успешно обновлена.");
        } else {
            System.out.println("Ошибка при обновлении задачи.");
        }
    }

    private static void deleteTask() {
        int id = readInt("Введите ID задачи для удаления: ");
        boolean deleted = dbHandler.deleteTask(id);
        if (deleted) {
            System.out.println("Задача успешно удалена.");
        } else {
            System.out.println("Задача с таким ID не найдена или ошибка при удалении.");
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }
}
