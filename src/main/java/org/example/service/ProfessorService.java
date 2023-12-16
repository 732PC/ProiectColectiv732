package org.example.service;

import org.example.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repository.ProfessorRepository;

import java.util.List;
import java.util.Optional;
@Service
public class ProfessorService {

   private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
    public void add(Professor professor){
        professorRepository.save(professor);
    }
    public Optional<Professor> findById(Integer id){
        return professorRepository.findById(id);
    }

    public List<Professor> findAll(){
        return professorRepository.findAll();
    }

    public void remove(Integer id){
        professorRepository.deleteById(id);
    }
    public void update(Integer id, Professor newProfessor){
        for(Professor professor: professorRepository.findAll())
            if(professor.getId().equals(id)){
                if(newProfessor.getCnp() != null){
                    professor.setCnp(newProfessor.getCnp());
                }
                if(newProfessor.getFirstName() != null) {
                    professor.setFirstName(newProfessor.getFirstName());
                }
                if(newProfessor.getLastName() != null) {
                    professor.setLastName(newProfessor.getLastName());
                }
                if(newProfessor.getBirthdate() != null) {
                    professor.setBirthdate(newProfessor.getBirthdate());
                }
                if(newProfessor.getCountry() != null) {
                    professor.setCountry(newProfessor.getCountry());
                }

                professorRepository.save(professor);
            }
    }
}
