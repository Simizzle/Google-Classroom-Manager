package com.techreturners.controller;

import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.Student;
import com.google.api.services.classroom.model.Topic;
import com.techreturners.ClassroomQuickstart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/googleClassroomManager")
public class GoogleClassroomManagerController {

    @Autowired
    ClassroomQuickstart classroomQuickstart;

    @GetMapping("/courses")
    public List<Course> getClassroomInfo() throws GeneralSecurityException, IOException {
        return classroomQuickstart.getCourses();
    }

    @GetMapping("/students")
    public List<Student> getStudentList() throws GeneralSecurityException, IOException {
        return classroomQuickstart.getStudentList();
    }

    @GetMapping("/topics")
    public List<Topic> getCourseTopics() throws GeneralSecurityException, IOException {
        return classroomQuickstart.getTopics();
    }

}
