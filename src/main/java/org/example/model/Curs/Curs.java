package org.example.model.Curs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Professors.Professor;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="curs")
public class Curs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Professor professor;

    @Column(name="numecurs")
    private String numecurs;

    @Column(name="numarcredite")
    private int numarcredite;

    @Column(name="oracurs")
    private Date oracurs;

    @Enumerated(EnumType.STRING)
    @Column(name="an", length = 5)
    private StudyYear an;

    @Enumerated(EnumType.STRING)
    @Column(name="tip", length = 10)
    private CourseType tip;

}