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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="students")
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
