package com.techreturners.service;

import com.google.api.services.classroom.model.StudentSubmission;
import com.techreturners.DAO.ClassroomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    ClassroomDAO classroomDAO;

    public List<StudentSubmission> getSubmissionForStudent(String courseId, String courseWorkId, String studentId)
            throws IOException, GeneralSecurityException {
        List<StudentSubmission> submissions = classroomDAO.getStudentSubmissions(courseId, courseWorkId);
        List<StudentSubmission> result = submissions.stream().filter(studentSubmission -> studentSubmission.getUserId().equals(studentId)).toList();
        return result;
    }
}
