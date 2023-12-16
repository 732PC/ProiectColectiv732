package org.example.model;

import lombok.NoArgsConstructor;
import org.example.enums.Countries;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity

@Getter
@Setter
@NoArgsConstructor
@Table(name = "professors")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "cnp", nullable = false)
    private Long cnp;
    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private Countries country;

    @OneToMany(mappedBy = "prof", cascade = CascadeType.ALL)
    List<Course> courses;


    public Professor(String firstName, String lastName, Long cnp, Date birthdate, Countries country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthdate = birthdate;
        this.country = country;
    }
}
