import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alefilas.config.JpaConfig;
import ru.alefilas.model.Student;

import ru.alefilas.repository.StudentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    private final List<Student> students;

    {
        Student student1 = new Student();
        student1.setName("Alex");
        student1.setSurname("Petrov");
        student1.setPatronymic("Ivanovic");
        student1.setBirthday(LocalDate.now());
        student1.setGroupName("1mTM");

        Student student2 = new Student();
        student2.setName("Petr");
        student2.setSurname("Ivanov");
        student2.setPatronymic("Ivanovic");
        student2.setBirthday(LocalDate.now());
        student2.setGroupName("2mTM");

        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
    }

    @Test
    public void saveTest() {
        Student student = students.get(0);

        repository.save(student);

        Student studentFromDb = repository.findById(student.getId()).orElseThrow();

        Assert.assertEquals(student, studentFromDb);

        repository.delete(studentFromDb);

        Optional<Student> studentOptional = repository.findById(studentFromDb.getId());

        Assert.assertFalse(studentOptional.isPresent());
    }

    @Test
    public void getListTest() {

        students.forEach(repository::save);

        List<Student> students = this.students.stream()
                .map(student -> repository.findById(student.getId()).orElseThrow())
                .collect(Collectors.toList());

        Assert.assertEquals(this.students, students);

        students.forEach(repository::delete);
    }

}
