package org.example.services;

import org.springframework.stereotype.Service;

import org.example.entities.Student;
import org.example.entities.Teacher;
import org.example.repositories.StudentRepository;
import org.example.repositories.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DBService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public DBService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    // ==== Student CRUD ====

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean existsStudentById(Long id) {
        return studentRepository.existsById(id);
    }

    // ==== Teacher CRUD ====

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public boolean existsTeacherById(Long id) {
        return teacherRepository.existsById(id);
    }

    // ==== Top Student by Subject ====

    public List<Student> getTopByKorean() {
        return studentRepository.findTopByKorean();
    }

    public List<Student> getTopByEnglish() {
        return studentRepository.findTopByEnglish();
    }

    public List<Student> getTopByMath() {
        return studentRepository.findTopByMath();
    }

    public List<Student> getTopByScience() {
        return studentRepository.findTopByScience();
    }

    public List<Student> getTopByHistory() {
        return studentRepository.findTopByHistory();
    }

    public List<Student> getTopByAverage() {
        return studentRepository.findTopByAverage();
    }
}
