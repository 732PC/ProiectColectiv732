package org.example.model.Student;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Curs.StudyYear;
import org.example.model.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="students")
public class Student extends User {

    public Student(long cnp, StudyYear an, Level nivel, Financing finantare, Date datanastere, @Nullable String liceu) {
        this.cnp = cnp;
        this.an = an;
        this.nivel = nivel;
        this.finantare = finantare;
        this.datanastere = datanastere;
        this.liceu = liceu;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name="cnp")
    private long cnp;

    @Column(name="an", length = 10)
    @Enumerated(EnumType.STRING)
    private StudyYear an;

    @Column(name="nivel",length = 10)
    @Enumerated(EnumType.STRING)
    private Level nivel;

    @Column(name="finantare", length = 20)
    @Enumerated(EnumType.STRING)
    private Financing finantare;

    @Column(name="liceu")
    @Nullable
    private String liceu;

    @Column(name="datanastere")
    private Date datanastere;



}
