package com.techreturners.controller;

import com.google.api.services.classroom.model.*;
import com.techreturners.DAO.ClassroomDAO;
import com.techreturners.service.ClassroomService;
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

    @Autowired
    ClassroomService classroomService;

    @GetMapping("/courses")
    public List<Course> getCourseList() throws GeneralSecurityException, IOException {
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

    @GetMapping("/coursework/{courseId}")
    public List<CourseWork> getCoursework(@PathVariable("courseId") String courseId) throws GeneralSecurityException, IOException {
        return classroomDAO.getCoursework(courseId);
    }

    @GetMapping("/student-submissions/{courseId}/{courseworkId}/{studentId}")
    public List<StudentSubmission> getStudentSubmission(@PathVariable("courseId") String courseId,
                                                        @PathVariable("courseworkId") String courseworkId,
                                                        @PathVariable("studentId") String studentId)
            throws GeneralSecurityException, IOException {
        return classroomDAO.getStudentSubmissions(courseId, courseworkId, studentId);
    }

    @GetMapping("/coursework/{courseId}/{courseworkId}/{studentId}")
    public List<StudentSubmission> getSubmissionsForStudent(@PathVariable("courseId") String courseId,
                                                            @PathVariable("courseworkId") String courseworkID,
                                                            @PathVariable("studentId") String studentId) throws GeneralSecurityException, IOException {
        return classroomDAO.getStudentSubmissions(courseId, courseworkID, studentId);
    }

    @GetMapping("/all-coursework/{courseId}/{studentId}")
    public List<StudentSubmission> getAllSubmissionsForStudent(@PathVariable("courseId") String courseId,
                                                               @PathVariable("studentId") String studentId) throws Throwable {
        return classroomService.getAllCourseworkForStudent(courseId, studentId);
    }

    @GetMapping("/attendance/{courseId}/{studentId}")
    public List<StudentSubmission> getAllAttendanceForAStudent(@PathVariable("courseId") String courseId,
                                                               @PathVariable("studentId") String studentId) throws Throwable {
        return classroomService.getAllAttendanceForAStudent(courseId, studentId);
    }

}
