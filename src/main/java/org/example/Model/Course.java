package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column
    private String courseName;

    @Column
    private int studyYear;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();
}
