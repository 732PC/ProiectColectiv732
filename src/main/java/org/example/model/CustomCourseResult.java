package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomCourseResult {
    private String courseName;
    private boolean isOptional;
    private String firstname;
    private String lastname;
    private String dayOfWeek;
    private String time;
}
