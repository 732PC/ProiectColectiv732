package org.example.model.Professors;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Curs.Curs;
import org.example.model.User;

import java.util.Date;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="professors")
public class Professor extends User {

    public Professor(long cnp, Date datanastere, String materie, @Nullable String taraorigine) {
        this.cnp = cnp;
        this.datanastere = datanastere;
        this.materie = materie;
        this.taraorigine = taraorigine;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="cnp")
    private long cnp;

    @Column(name="datanastere")
    private Date datanastere;

    @Column(name="materie")
    private String materie;

    @Nullable
    @Column(name="taraorigine", length = 20)
    private String taraorigine;
}

