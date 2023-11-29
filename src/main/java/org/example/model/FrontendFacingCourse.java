package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FrontendFacingCourse {
    @Getter
    @Setter
    private String course_name;
    @Getter
    @Setter
    private boolean is_optional;
    @Getter
    @Setter
    private String firstname;
    @Getter
    @Setter
    private String lastname;
}
