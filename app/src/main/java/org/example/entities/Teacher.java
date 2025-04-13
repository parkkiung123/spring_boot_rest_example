package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TEACHER", schema = "ADMIN")  // Oracle 스키마 명시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "CLASS")
    private Integer classNum;
}
