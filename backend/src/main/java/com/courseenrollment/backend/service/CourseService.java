package com.courseenrollment.backend.service;

import com.courseenrollment.backend.entity.Course;
import com.courseenrollment.backend.entity.Enrollment;
import com.courseenrollment.backend.entity.User;
import com.courseenrollment.backend.repository.CourseRepository;
import com.courseenrollment.backend.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Course addCourse(Course course) {
        Course newCourse = new Course();
        newCourse.setCourseName(course.getCourseName());
        newCourse.setCapacity(course.getCapacity());
        courseRepository.save(newCourse);
        return newCourse;
    }

    public Optional<Course> findCourseById(Integer id) {
        Optional<Course> course = courseRepository.findById(id);
        return course;
    }

    public Course deleteCourse(Course course) {
        courseRepository.delete(course);
        return course;
    }

    public List<Course> getAllAvailableCoursesByUser(Optional<User> user) {
        System.out.println(user.get().getUserId());
        List<Course> allCourses = courseRepository.findAllAvailableCoursesByUser(user.get().getUserId());
        System.out.println(allCourses);
        return allCourses;
    }

    public List<Course> getAllAvailableCourses() {
        List<Course> allCourses = courseRepository.findAllAvailableCourses();
        System.out.println(allCourses);
        return allCourses;
    }

    public List<Course> getCoursesBySearch(String search) {
        return courseRepository.findCoursesBySearch(search);
    }

    public Course updateCourse(Course oldCourse, Course newCourse) {
        oldCourse.setCourseName(newCourse.getCourseName());
        oldCourse.setProfessorName(newCourse.getProfessorName());
        oldCourse.setCapacity(newCourse.getCapacity());
        return courseRepository.save(oldCourse);
    }
}
