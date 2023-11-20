package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="courseName")
    private String name;
    @Column(name="abbreviation")
    private String abbreviation;
    @Column(name="creditNr")
    private double creditNr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Professor prof;
    @Column(name="yearOfStudy")
    private int yearOfStudy;
    @Enumerated(EnumType.STRING)
    @Column(name="type",length=20)
    private CourseType type;
}
