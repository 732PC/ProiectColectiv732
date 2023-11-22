package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Students")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentID;

    @Column(name="lastname")
    private String lastname;

    @Column(name="firstname")
    private String firstname;

    @Enumerated(EnumType.STRING)
    @Column(name="role", length = 15)
    private ERole role;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;
    @Nullable
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<StudentCourse> studentCourses;

    //public Set
//    public void enrollInCourse(Course course){
//        studentCourses.add(course);
//    }

}
