package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseMaterialResponse {
    private Long materialId;
    private Integer courseId;
    private String content;
    private List<CourseMaterial> courseMaterials;

    public CourseMaterialResponse(Integer courseId, String content) {
        this.courseId = courseId;
        this.content = content;
    }
}
