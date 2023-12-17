package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

public class HomeworkSubmissionResponse {
    private Long submissionId;
    private Integer studentId;
    private String homeworkSubmissionText;
    private LocalDateTime submissionDate;
    private List<HomeworkSubmission> homeworkSubmissions;

    public List<HomeworkSubmission> getHomeworkSubmissions() {
        return homeworkSubmissions;
    }



    public HomeworkSubmissionResponse(Integer studentId, String homeworkSubmissionText, LocalDateTime submissionDate) {
        this.studentId = studentId;
        this.homeworkSubmissionText = homeworkSubmissionText;
        this.submissionDate = submissionDate;
    }

    public HomeworkSubmissionResponse() {
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getStudentID() {
        return studentId;
    }

    public String getHomeworkSubmissionText() {
        return homeworkSubmissionText;
    }

    public void setHomeworkSubmissionText(String homeworkSubmissionText) {
        this.homeworkSubmissionText = homeworkSubmissionText;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public int getSubmissionId() {
        return Math.toIntExact(submissionId);
    }
}
