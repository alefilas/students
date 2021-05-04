package ru.alefilas.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import ru.alefilas.model.Student;
import ru.alefilas.repository.StudentRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuRunner {

    private final StudentRepository repository;
    private final Scanner scanner;

    @Autowired
    public MenuRunner(StudentRepository repository) {
        this.repository = repository;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean isExit = false;

        while (!isExit) {

            System.out.println("\nChoose action(1 - add, 2 - delete, 3 - get student, 4 - list, 5 - exit):");
            String answer = scanner.nextLine();

            switch (answer) {
                case "5" -> isExit = true;
                case "1" -> addStudent();
                case "2" -> deleteStudent();
                case "3" -> showStudent();
                case "4" -> showStudentsList();
                default -> System.out.println("Incorrect input");
            }
        }
    }

    private void showStudentsList() {
        System.out.println(repository.findAll());
    }

    private void deleteStudent() {
        System.out.println("Enter student id:");
        Long id = null;
        try {
            id = Long.parseLong(scanner.nextLine());
            repository.deleteById(id);
            System.out.println("Student was deleted");
        } catch (NumberFormatException e) {
            System.out.println("It's not a number");
        } catch (EmptyResultDataAccessException e) {
            System.out.println("There is no student with id = " + id);
        }
    }

    private void showStudent() {
        System.out.println("Enter student id:");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Student> student = repository.findById(id);

            if (student.isPresent()) {
                System.out.println(student.get());
            } else {
                System.out.println("There is no student with id = " + id);
            }

        } catch (NumberFormatException e) {
            System.out.println("It's not a number");
        }
    }

    private void addStudent() {
        Student student = new Student();

        System.out.println("Enter name:");
        student.setName(scanner.nextLine());

        System.out.println("Enter surname:");
        student.setSurname(scanner.nextLine());

        System.out.println("Enter patronymic:");
        student.setPatronymic(scanner.nextLine());

        System.out.println("Enter group:");
        student.setGroupName(scanner.nextLine());

        System.out.println("Enter birthday in format YYYY-MM-DD:");
        try {
            student.setBirthday(LocalDate.parse(scanner.nextLine()));
        } catch (Exception e) {
            System.out.println("Incorrect format");
        }

        if (student.getBirthday() != null) {
            repository.save(student);
            System.out.println(student + " was added");
        }
    }
}
