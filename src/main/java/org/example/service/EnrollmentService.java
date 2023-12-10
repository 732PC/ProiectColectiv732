package org.example.service;

import org.example.model.Enrollment;
import org.example.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void createCSV(){
        String jdbcURL = "jdbc:mysql://localhost:3306/Enrollment";
        String username = "sergey";
        String password = "sergey";

        String csvFilePath = "Enrollment_export.csv";

        try(Connection connection = DriverManager.getConnection(jdbcURL, username, password)){
            String sql = "select * from attendencies";

            Statement statemnent = connection.createStatement();

            ResultSet result = statemnent.executeQuery(sql);

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            fileWriter.write("Curs, Student, Attendance");
            while(!result.next()){
                int id = result.getInt("id");
                String courseId = result.getString("curs_id");
                String studentId = result.getString("student_id");
                String attendance = result.getString("attendance");

                String line = String.format("\"%d\",%s,%s,%s",
                        id, courseId, studentId, attendance);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            statemnent.close();
            fileWriter.close();
        }
        catch(SQLException e){
            System.out.println("Database error: ");
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("File error");
            e.printStackTrace();
        }
    }
}
