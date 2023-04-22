package com.courseenrollment.backend.controller;

import com.courseenrollment.backend.entity.Course;
import com.courseenrollment.backend.entity.User;
import com.courseenrollment.backend.model.CourseDetail;
import com.courseenrollment.backend.model.UserPassword;
import com.courseenrollment.backend.service.CourseService;
import com.courseenrollment.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
public class UserController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/course/all/{userId}")
    public ResponseEntity<List<Course>> getAllAvailableCoursesByUserId(@PathVariable Integer userId){
        Optional<User> oldUser = userService.findUserById(userId);
        if(oldUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllAvailableCoursesByUser(oldUser));
    }

    @GetMapping("/course/all")
    public List<Course> getAllAvailableCourses(){
        return courseService.getAllAvailableCourses();
    }

    @GetMapping("/course/search/{search}")
    public List<Course> getCoursesBySearch(@PathVariable String search){
        return courseService.getCoursesBySearch(search);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id){
        Optional<Course> course = courseService.findCourseById(id);
        if(course.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(course.get());
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer id, @RequestBody Course newCourse){
        Optional<Course> oldCourse = courseService.findCourseById(id);
        if(oldCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Course course = courseService.updateCourse(oldCourse.get(), newCourse);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User newUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @PostMapping("/user/check")
    public ResponseEntity<User> checkUser(@RequestBody UserPassword userPassword){
        Optional<User> user = userService.checkCredentials(userPassword);

        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CourseDetail>> getUserCourses(@PathVariable Integer id){
        Optional<User> oldUser = userService.findUserById(id);
        if(oldUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<CourseDetail> courseDetailsList = userService.getUserCourses(oldUser);
        return ResponseEntity.status(HttpStatus.OK).body(courseDetailsList);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User newUser){
        Optional<User> oldUser = userService.findUserById(id);
        if(oldUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        User user = userService.updateUser(oldUser.get(), newUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/enroll/user/{userId}/course")
    public ResponseEntity<User> enrollCourse(@PathVariable Integer userId , @RequestBody List<Integer> courseIdList){
        Optional<User> user = userService.findUserById(userId);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        userService.addCourse(user.get(), courseIdList);
        return ResponseEntity.status(HttpStatus.OK).body(new User());
    }
}
