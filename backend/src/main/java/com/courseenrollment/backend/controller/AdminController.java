package com.courseenrollment.backend.controller;

import com.courseenrollment.backend.entity.Course;
import com.courseenrollment.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/course")
@CrossOrigin()
public class AdminController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        Course newCourse = courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.OK).body("Course Added");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id){
        Optional<Course> course = courseService.findCourseById(id);
        if(course.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find the Id");
        }
        courseService.deleteCourse(course.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted the course");
    }
}
