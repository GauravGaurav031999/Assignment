package com.example.Student_Application.service;

import com.example.Student_Application.model.Student;
import com.example.Student_Application.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        student.updateAge();
        Student savedStudent = studentRepository.save(student);
        logger.info("Student saved successfully: {}", savedStudent);
        return savedStudent;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        logger.info("Fetched {} students from the database.", students.size());
        return students;
    }

    public Student updateStudentById(Long id, Student updatedDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedDetails.getName());
                    student.setYearOfBirth(updatedDetails.getYearOfBirth());
                    student.setMonthOfBirth(updatedDetails.getMonthOfBirth());
                    student.setDayOfBirth(updatedDetails.getDayOfBirth());

                    // Recalculate and update age
                    int newAge = calculateAge(student.getYearOfBirth(), student.getMonthOfBirth(), student.getDayOfBirth());
                    student.setAge(newAge);

                    Student savedStudent = studentRepository.save(student);
                    logger.info("Updated student ID {}: New Age = {}", id, newAge);
                    return savedStudent;
                })
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    public int calculateAge(int year, int month, int day) {
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }


    public List<Student> getStudentsBetweenAges(int minAge, int maxAge) {
        List<Student> students = studentRepository.findByAgeBetween(minAge, maxAge);
        logger.info("Found {} students between {} and {} years old.", students.size(), minAge, maxAge);
        return students;
    }
}
