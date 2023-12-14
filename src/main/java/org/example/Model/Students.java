package org.example.Model;
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
    private Integer studentId;

    @Column(name =  "lastName")
    private String lastname;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "studyYear")
    private Integer studyYear;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments = new HashSet<>();
}
