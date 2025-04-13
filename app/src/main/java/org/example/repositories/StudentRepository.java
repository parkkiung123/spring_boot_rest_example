package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.example.entities.Student;

import java.util.List;

// @RepositoryRestResource는 이 리포지토리의 REST 경로를 /students로 지정합니다.
// 생략해도 기본 경로는 클래스명 기준 자동 생성됩니다.
@RepositoryRestResource(path = "students")
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.korean = (SELECT MAX(s2.korean) FROM Student s2)")
    List<Student> findTopByKorean();

    @Query("SELECT s FROM Student s WHERE s.english = (SELECT MAX(s2.english) FROM Student s2)")
    List<Student> findTopByEnglish();

    @Query("SELECT s FROM Student s WHERE s.math = (SELECT MAX(s2.math) FROM Student s2)")
    List<Student> findTopByMath();

    @Query("SELECT s FROM Student s WHERE s.science = (SELECT MAX(s2.science) FROM Student s2)")
    List<Student> findTopByScience();

    @Query("SELECT s FROM Student s WHERE s.history = (SELECT MAX(s2.history) FROM Student s2)")
    List<Student> findTopByHistory();

    @Query(
    value = "SELECT * FROM student ORDER BY (korean + english + math + science + history)/5.0 DESC FETCH FIRST 1 ROWS ONLY",
        nativeQuery = true
    )
    List<Student> findTopByAverage();


}