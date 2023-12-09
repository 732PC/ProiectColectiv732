package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Curs.Curs;
import org.example.model.Student.AttendanceStatus;
import org.example.model.Student.Student;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "enrollments")
public class Enrollment {

    public Enrollment(Student student, Curs curs, AttendanceStatus attendance) {
        this.student = student;
        this.curs = curs;
        this.attendance = attendance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "curs_id")
    private Curs curs;

    @Column(name="status", length = 20)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendance;


}
