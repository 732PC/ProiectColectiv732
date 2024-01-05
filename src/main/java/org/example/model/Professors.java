package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class Professors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer professorID;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 15)
    private ERole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Countries country;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column (name = "cnp")
    private String cnp;

    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Course> courses;

}