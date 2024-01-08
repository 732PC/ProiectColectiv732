package org.example.Key;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentCourseKey implements Serializable {

    @Column(name="studentFK")
    private Integer studentID;
    @Column(name="courseFK")
    private Integer courseID;
}
