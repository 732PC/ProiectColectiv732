package org.example.model.Curs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Professors.Professor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="curs")
public class Curs {

    public Curs(Professor professor, String numecurs, int numarcredite, Date oracurs, AnStudiu an, TipCurs tip) {
        this.professor = professor;
        this.numecurs = numecurs;
        this.numarcredite = numarcredite;
        this.oracurs = oracurs;
        this.an = an;
        this.tip = tip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Professor professor;

    @Column(name="nume")
    private String numecurs;

    @Column(name="numarcredite")
    private int numarcredite;

    @Column(name="oracurs")
    private Date oracurs;

    @Enumerated(EnumType.STRING)
    @Column(name="an", length = 5)
    private AnStudiu an;

    @Enumerated(EnumType.STRING)
    @Column(name="tip", length = 10)
    private TipCurs tip;

}