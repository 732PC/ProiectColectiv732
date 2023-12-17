package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.HomeworkSubmission;
import org.example.model.HomeworkSubmissionResponse;
import org.example.model.Students;
import org.example.repository.HomeworkSubmissionRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private HomeworkSubmissionRepository homeworkSubmissionRepository;


    private static final int CNP_LENGTH = 13;

    public StudentService(StudentRepository studentRepository, HomeworkSubmissionRepository homeworkSubmissionRepository) {
    }

    private boolean isValidCnpLength(String cnp) {
        return cnp != null && cnp.length() == CNP_LENGTH;
    }

    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Students> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }

    public Optional<Students> updateStudent(int studentId, Students updatedStudent) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students savedStudent = existingStudent.get();

            savedStudent.setFirstname(updatedStudent.getFirstname());
            if (updatedStudent.getLastname() != null) {
                savedStudent.setLastname(updatedStudent.getLastname());
            }
            if (updatedStudent.getCnp() != null) {
                savedStudent.setCnp(updatedStudent.getCnp());
            }
            if (updatedStudent.getBirthDate() != null) {
                savedStudent.setBirthDate(updatedStudent.getBirthDate());
            }
            if (updatedStudent.getStudyYear() != 0) {
                savedStudent.setStudyYear(updatedStudent.getStudyYear());
            }
            if (updatedStudent.getStudyLevel() != null) {
                savedStudent.setStudyLevel(updatedStudent.getStudyLevel());
            }
            if (updatedStudent.getFundingForm() != null) {
                savedStudent.setFundingForm(updatedStudent.getFundingForm());
            }
            if (updatedStudent.getGraduatedHighSchool() != null) {
                savedStudent.setGraduatedHighSchool(updatedStudent.getGraduatedHighSchool());
            }

            Students updatedSavedStudent = studentRepository.save(savedStudent);
            return Optional.of(updatedSavedStudent);
        } else {
            return Optional.empty();
        }
    }




    public boolean deleteStudent(Integer id) {
        Optional<Students> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            studentRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Student with ID " + id + " not found");
        }
    }

    public Students addStudent(
            String firstName,
            String lastName,
            String cnp,
            LocalDate birthDate,
            int studyYear,
            String studyLevel,
            String fundingForm,
            String graduatedHighSchool) {

        Students student = new Students();
        student.setFirstname(firstName);
        student.setLastname(lastName);
        student.setCnp(cnp);
        student.setBirthDate(birthDate);
        student.setStudyYear(studyYear);
        student.setStudyLevel(studyLevel);
        student.setFundingForm(fundingForm);
        student.setGraduatedHighSchool(graduatedHighSchool);

        if (isValidCnpLength(student.getCnp())) {
            return studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Invalid CNP length. CNP must be exactly 13 digits.");
        }
    }

    public HomeworkSubmissionResponse addHomeworkSubmission(Integer studentId, String submissionText) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found"));

        HomeworkSubmission homeworkSubmission = new HomeworkSubmission();
        homeworkSubmission.setSubmissionText(submissionText);
        homeworkSubmission.setSubmissionDate(LocalDateTime.now());

        homeworkSubmission.setStudent(student);

        if (student.getHomeworkSubmissions() == null) {
            student.setHomeworkSubmissions(new ArrayList<>());
        }

        student.getHomeworkSubmissions().add(homeworkSubmission);

        HomeworkSubmission savedSubmission = homeworkSubmissionRepository.save(homeworkSubmission);

        student.getHomeworkSubmissions().add(savedSubmission);

        HomeworkSubmissionResponse response = new HomeworkSubmissionResponse();
        response.setSubmissionId(savedSubmission.getSubmissionId());

        return response;
    }

    public List<HomeworkSubmission> getAllHomeworkSubmissions(Integer studentId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId +
                        " not found"));

        return student.getHomeworkSubmissions();
    }

    public Optional<HomeworkSubmission> getHomeworkSubmissionById(Integer studentId, Integer submissionId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found"));

        return student.getHomeworkSubmissions().stream()
                .filter(submission -> submission.getSubmissionId().equals(submissionId))
                .findFirst();
    }

    public Optional<HomeworkSubmission> updateHomeworkSubmission(Integer studentId, Integer submissionId, String updatedText) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students student = existingStudent.get();
            Optional<HomeworkSubmission> existingSubmission = student.getHomeworkSubmissions().stream()
                    .filter(submission -> submission.getSubmissionId().equals(submissionId))
                    .findFirst();

            if (existingSubmission.isPresent()) {
                HomeworkSubmission submission = existingSubmission.get();
                submission.setSubmissionText(updatedText);

                return Optional.of(homeworkSubmissionRepository.save(submission));
            }
        }

        return Optional.empty();
    }



    public boolean deleteHomeworkSubmission(Integer studentId, Long submissionId) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students student = existingStudent.get();

            student.getHomeworkSubmissions().removeIf(submission -> submission.getSubmissionId().equals(submissionId));

            studentRepository.save(student);

            return true;
        }

        return false;
    }


    public Optional<HomeworkSubmission> updateHomeworkSubmission(Integer studentId, Long submissionId, String updatedText) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students student = existingStudent.get();
            Optional<HomeworkSubmission> existingSubmission = student.getHomeworkSubmissions().stream()
                    .filter(submission -> submission.getSubmissionId().equals(submissionId))
                    .findFirst();

            if (existingSubmission.isPresent()) {
                HomeworkSubmission submission = existingSubmission.get();
                submission.setSubmissionText(updatedText);

                return Optional.of(homeworkSubmissionRepository.save(submission));
            }
        }

        return Optional.empty();
    }

    public Optional<HomeworkSubmission> updateHomeworkSubmissionById(Integer studentId, Integer submissionId, String updatedText) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students student = existingStudent.get();
            Optional<HomeworkSubmission> existingSubmission = student.getHomeworkSubmissions().stream()
                    .filter(submission -> submission.getSubmissionId().equals(submissionId))
                    .findFirst();

            if (existingSubmission.isPresent()) {
                HomeworkSubmission submission = existingSubmission.get();
                submission.setSubmissionText(updatedText);

                HomeworkSubmission updatedSubmission = homeworkSubmissionRepository.save(submission);
                return Optional.of(updatedSubmission);
            }
        }

        return Optional.empty();
    }

}
