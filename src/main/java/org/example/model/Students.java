package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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

    @Column(name = "firstname")
  @NonNull
    private String firstname;

    @Column(name = "lastname")
    @NonNull
    private String lastname;

    @Column(name = "cnp")
    @NonNull
    private String cnp;

    @Column(name = "birthDate")
    @NonNull
    private LocalDate birthDate;

    @Column(name = "studyYear")
    @NonNull
    private int studyYear;

    @Column(name = "studyLevel")
    @NonNull
    private String studyLevel;

    @Column(name = "fundingForm")
    @NonNull
    private String fundingForm;

    @Column(name = "graduatedHighSchool")
    @NonNull
    private String graduatedHighSchool;
}
