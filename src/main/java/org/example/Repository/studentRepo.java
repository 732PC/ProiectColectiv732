package org.example.Repository;

import org.example.Model.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface studentRepo extends JpaRepository<Students,Integer> {

}
