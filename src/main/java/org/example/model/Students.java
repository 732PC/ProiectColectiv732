package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentID")
    private Integer studentID;

    @Column(name = "first_name")
    @NonNull
    private String firstname;

    @Column(name = "last_name")
    @NonNull
    private String lastname;

    @Column(name = "cnp")
    @NonNull
    private String cnp;

    @Column(name = "birthDate")
    @NonNull
    private LocalDate birthDate;

    @Column(name = "study_Year")
    @NonNull
    private int studyYear;

    @Column(name = "study_Level")
    @NonNull
    private String studyLevel;

    @Column(name = "funding_Form")
    @NonNull
    private String fundingForm;

    @Column(name = "graduated_HighSchool")
    @NonNull
    private String graduatedHighSchool;
}
