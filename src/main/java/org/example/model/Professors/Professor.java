package org.example.model.Professors;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.User;
import java.util.Date;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="professors")
public class Professor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

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

