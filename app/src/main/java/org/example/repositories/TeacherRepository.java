package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.example.entities.Teacher;

@RepositoryRestResource(path = "teachers")
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
}
