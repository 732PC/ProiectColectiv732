package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("student")
    private List<HomeworkSubmission> homeworkSubmissions = new ArrayList<>();

    public Students(int i, String john, String doe, String s, LocalDate of, int i1, String bachelor, String s1, String highSchool) {
    }
}
