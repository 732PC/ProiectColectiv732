package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    @NotNull
    private String firstname;
    @Column(name = "lastname")
    @NotNull
    private String lastname;
    @Column(name = "cnp")
    @NotNull
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