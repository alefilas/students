package ru.alefilas.repository;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import ru.alefilas.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

}
