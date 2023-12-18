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
@Table(name="attendencies")
public class Attendencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="curs_id")
    private int curs_id;

    @Column(name="student_id")
    private int student_id;

    @Column(name="attendance", length = 20)
    @Enumerated(EnumType.STRING)
    private Attendance attendance;

    @Column(name="grade")
    private int grade;

}
