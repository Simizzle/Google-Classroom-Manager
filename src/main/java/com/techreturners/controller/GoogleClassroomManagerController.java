package com.techreturners.controller;

import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.Student;
import com.google.api.services.classroom.model.Topic;
import com.techreturners.DAO.ClassroomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/googleClassroomManager")
public class GoogleClassroomManagerController {

    @Autowired
    ClassroomDAO classroomDAO;

    @GetMapping("/courses")
    public List<Course> getClassroomInfo() throws GeneralSecurityException, IOException {
        return classroomDAO.getCourses();
    }

    @GetMapping("/students/{courseId}")
    public List<Student> getStudentList(@PathVariable("courseId") String courseId) throws GeneralSecurityException, IOException {
        return classroomDAO.getStudentList(courseId);
    }

    @GetMapping("/topics/{courseId}")
    public List<Topic> getCourseTopics(@PathVariable("courseId") String courseId) throws GeneralSecurityException, IOException {
        return classroomDAO.getTopics(courseId);
    }

}
