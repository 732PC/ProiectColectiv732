package org.example.entities;

import lombok.NoArgsConstructor;
import org.example.enums.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
@Entity

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "professor")
public class Professor {
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "cnp")
    private Long cnp;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private Countries country;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Professor(String firstName, String lastName, Long cnp, Date birthdate, Countries country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthdate = birthdate;
        this.country = country;
    }
}
