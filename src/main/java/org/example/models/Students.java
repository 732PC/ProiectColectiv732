package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
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

    @Column(name = "first_name")
    @Size(min = 1)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "cnp")
    @NotNull
    @Size(min = 13, max = 13, message = "CNP must be exactly 13 digits")
    private String cnp;

    @Column(name = "birth_date")
    @NotNull
    private LocalDate birthDate;

    @Column(name = "study_year")
    @NotNull
    private int studyYear;

    @Column(name = "study_level")
    @NotNull
    private String studyLevel;

    @Column(name = "funding_form")
    @NotNull
    private String fundingForm;

    @Column(name = "graduated_high_school")
    @NotNull
    private String graduatedHighSchool;
}
