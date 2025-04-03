package com.example.Student_Application.controller;

import com.example.Student_Application.service.StudentService;
import com.example.Student_Application.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        logger.info("Received request to save student: {}", student);
        Student savedStudent = studentService.saveStudent(student);
        logger.info("Successfully saved student with ID: {}", savedStudent.getId());
        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        logger.info("Received request to fetch all students");
        List<Student> students = studentService.getAllStudents();
        logger.info("Returning {} students", students.size());
        return ResponseEntity.ok(students);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudentDetails) {

        logger.info("Received request to update student with ID: {}", id);

        Student updatedStudent = studentService.updateStudentById(id, updatedStudentDetails);

        logger.info("Successfully updated student with ID: {}", updatedStudent.getId());
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("/between-ages")
    public ResponseEntity<List<Student>> getStudentsBetweenAges(@RequestParam int minAge, @RequestParam int maxAge) {
        logger.info("Received request to fetch students between ages {} and {}", minAge, maxAge);
        List<Student> students = studentService.getStudentsBetweenAges(minAge, maxAge);
        logger.info("Found {} students in the given age range", students.size());
        return ResponseEntity.ok(students);
    }
}
