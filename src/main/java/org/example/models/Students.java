package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Students {
    public Students(String firstName, String lastName, String cnp, LocalDate birthDate, int studyYear, String studyLevel, String fundingForm, String graduatedHighSchool) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.birthDate = birthDate;
        this.studyYear = studyYear;
        this.studyLevel = studyLevel;
        this.fundingForm = fundingForm;
        this.graduatedHighSchool = graduatedHighSchool;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstName")
    @Size(min = 1)
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "cnp")
    @NotNull
    @Size(min = 13, max = 13, message = "CNP must be exactly 13 digits")
    private String cnp;

    @Column(name = "birthDate")
    @NotNull
    private LocalDate birthDate;

    @Column(name = "studyYear")
    @NotNull
    private int studyYear;

    @Column(name = "studyLevel")
    @NotNull
    private String studyLevel;

    @Column(name = "fundingForm")
    @NotNull
    private String fundingForm;

    @Column(name = "graduatedHighSchool")
    @NotNull
    private String graduatedHighSchool;
}
