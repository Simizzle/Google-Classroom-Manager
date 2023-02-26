package com.techreturners.service;

import com.google.api.services.classroom.model.CourseWork;
import com.google.api.services.classroom.model.StudentSubmission;
import com.techreturners.DAO.ClassroomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    @Autowired
    ClassroomDAO classroomDAO;

    @Async
    public CompletableFuture<List<StudentSubmission>> getOneSubmissionForStudent(String courseId, String courseWorkId, String studentId)
            throws IOException, GeneralSecurityException {
        List<StudentSubmission> submissions = classroomDAO.getStudentSubmissions(courseId, courseWorkId, studentId);
        return CompletableFuture.completedFuture(submissions);
    }

    @Async
    public CompletableFuture<List<CourseWork>> getAsyncListOfCoursework(String courseId)
            throws IOException, GeneralSecurityException {
        List<CourseWork> coursework = classroomDAO.getCoursework(courseId);
        return CompletableFuture.completedFuture(coursework);
    }

    public List<StudentSubmission> getAllCourseworkForStudent(String courseId, String studentId) throws Throwable {
        CompletableFuture<List<CourseWork>> result = getAsyncListOfCoursework(courseId);
        CompletableFuture<List<StudentSubmission>> coursework;
        List<String> courseworkIds;
        List<StudentSubmission> submissions = new ArrayList<>();
        courseworkIds = result.get().stream().map(CourseWork::getId).collect(Collectors.toList());
        try {
            for (String courseworkId : courseworkIds) {
                coursework = getOneSubmissionForStudent(courseId, courseworkId, studentId);
                List<StudentSubmission> submission = coursework.get();
                submissions.addAll(submission);
            }
        } catch (Throwable e) {
            throw e.getCause();
        }
        return submissions;
    }

    public List<StudentSubmission> getAllAttendanceForAStudent(String courseId, String studentId) throws Throwable {
        CompletableFuture<List<CourseWork>> result = getAsyncListOfCoursework(courseId);
        CompletableFuture<List<StudentSubmission>> coursework;
        List<String> courseworkIds;
        List<StudentSubmission> submissions = new ArrayList<>();
        courseworkIds = result.get().stream().map(CourseWork::getId).collect(Collectors.toList());
        try {
            for (String courseworkId : courseworkIds) {
                coursework = getOneSubmissionForStudent(courseId, courseworkId, studentId);
                List<StudentSubmission> submission = coursework.get().stream().filter(studentSubmission -> studentSubmission.getCourseWorkType().equals("MULTIPLE_CHOICE_QUESTION")).toList();
                submissions.addAll(submission);
            }
            return submissions;
        } catch (Throwable e) {
            throw e.getCause();
        }
    }

    public float getAttendancePercentage(String courseId, String studentId) throws Throwable {
        CompletableFuture<List<CourseWork>> listOfCoursework = getAsyncListOfCoursework(courseId);
        CompletableFuture<List<StudentSubmission>> attendanceQuestion;
        List<String> attendanceQuestionIds;
        List<StudentSubmission> listOfAttendanceQuestions = new ArrayList<>();
        attendanceQuestionIds = listOfCoursework.get().stream().filter(coursework -> (coursework.getTitle().contains("Attendance") && coursework.getWorkType().equals("MULTIPLE_CHOICE_QUESTION")))
                .map(CourseWork::getId).toList();
        int count = 0;
        try {
            for (String attendanceIds : attendanceQuestionIds) {
                attendanceQuestion = getOneSubmissionForStudent(courseId, attendanceIds, studentId);
                List<StudentSubmission> submissions = attendanceQuestion.get().stream().toList();
                listOfAttendanceQuestions.addAll(submissions);
                count++;
            }
        } catch (Throwable e) {
            throw e.getCause();
        }
        long present = listOfAttendanceQuestions.stream().filter(attendance ->
                attendance.getState().equals("RETURNED") || attendance.getState().equals("TURNED_IN")).count();

        return (present * 100 / count);
    }
}
