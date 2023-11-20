package org.example.services;


import org.example.models.Students;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

//    @Autowired
//    private Validator validator;

    public List<Students> getAllStudents(){
        return studentRepository.findAll();
    }

    public Optional<Students> getStudentById(Integer id){
        return studentRepository.findById(id);
    }

    public Students addStudent(Students student){
        student.setId(null);
        return studentRepository.save(student);
    }



    public Optional<Students> updateStudent(int studentId, Students updatedStudent) {
        Optional<Students> existingStudent = studentRepository.findById(studentId);

        if (existingStudent.isPresent()) {
            Students savedStudent = existingStudent.get();

            savedStudent.setFirstName(updatedStudent.getFirstName());
            savedStudent.setLastName(updatedStudent.getLastName());
            savedStudent.setCnp(updatedStudent.getCnp());
            savedStudent.setBirthDate(updatedStudent.getBirthDate());
            savedStudent.setStudyYear(updatedStudent.getStudyYear());
            savedStudent.setStudyLevel(updatedStudent.getStudyLevel());
            savedStudent.setFundingForm(updatedStudent.getFundingForm());
            savedStudent.setGraduatedHighSchool(updatedStudent.getGraduatedHighSchool());

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




}
