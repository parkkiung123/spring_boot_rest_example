package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STUDENT", schema = "ADMIN") // Oracle에서 스키마명 명시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "CLASS")
    private Integer classNum;

    @Column(name = "TEACHER", length = 100)
    private String teacher;

    @Column(name = "KOREAN")
    private Integer korean;

    @Column(name = "ENGLISH")
    private Integer english;

    @Column(name = "MATH")
    private Integer math;

    @Column(name = "SCIENCE")
    private Integer science;

    @Column(name = "HISTORY")
    private Integer history;
}
