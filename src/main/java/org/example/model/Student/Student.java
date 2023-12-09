package org.example.model.Student;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Curs.AnStudiu;
import org.example.model.Curs.Curs;
import org.example.model.Enrollment;
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

    public Student(long cnp, AnStudiu an, Nivel nivel, Finantare finantare, Date datanastere, @Nullable String liceu) {
        this.cnp = cnp;
        this.an = an;
        this.nivel = nivel;
        this.finantare = finantare;
        this.datanastere = datanastere;
        this.liceu = liceu;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @MapsId
    @OneToOne
    private User user;

    @Column(name="cnp")
    private long cnp;

    @Column(name="an", length = 10)
    @Enumerated(EnumType.STRING)
    private AnStudiu an;

    @Column(name="nivel",length = 10)
    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Column(name="finantare", length = 20)
    @Enumerated(EnumType.STRING)
    private Finantare finantare;

    @Column(name="liceu")
    @Nullable
    private String liceu;

    @Column(name="datanastere")
    private Date datanastere;



}
