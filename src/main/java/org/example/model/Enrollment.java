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
@Table(name = "enrollments")
public class Enrollment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "curs_id")
    private Course curs;


}
