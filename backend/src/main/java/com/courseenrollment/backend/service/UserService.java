package com.courseenrollment.backend.service;

import com.courseenrollment.backend.entity.Course;
import com.courseenrollment.backend.entity.Enrollment;
import com.courseenrollment.backend.entity.User;
import com.courseenrollment.backend.model.CourseDetail;
import com.courseenrollment.backend.model.UserPassword;
import com.courseenrollment.backend.repository.CourseRepository;
import com.courseenrollment.backend.repository.EnrollmentRepository;
import com.courseenrollment.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public User saveUser(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmailId(user.getEmailId());
        newUser.setContactNo(user.getContactNo());

        return userRepository.save(user);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user, User newUser) {
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setContactNo(newUser.getContactNo());
        user.setEmailId(newUser.getEmailId());

        return userRepository.save(user);
    }

    public Optional<User> checkCredentials(UserPassword userPassword){
        Optional<User> user = userRepository.findByloginId(userPassword.getLoginId());
        if(user.isEmpty()){
            return Optional.ofNullable(null);
        }
        System.out.println(user.get().getLoginId());
        if(user.get().getPassword().equals(userPassword.getPassword())){
            System.out.println("userPassword "+user.get().getUserId());
            return user;
        }
        return Optional.ofNullable(null);
    }

    public List<CourseDetail> getUserCourses(Optional<User> oldUser) {
        List<Enrollment> enrollmentList = enrollmentRepository.findAllByUser(oldUser);
        List<CourseDetail> courseDetail = new ArrayList<>();
        enrollmentList.stream().forEach(enrollment -> {
            System.out.println(enrollment.getCourse().getCourseName());
            courseDetail.add(new CourseDetail(enrollment.getCourse().getCourseName(), enrollment.getCourse().getProfessorName()));
        });
        System.out.println(courseDetail);
        return courseDetail;
    }

    public void addCourse(User user, List<Integer> courseIdList) {
        courseIdList.forEach(courseId -> {
            Optional<Course> course = courseRepository.findById(courseId);
            Enrollment enrollment =  new Enrollment(course.get(), user);
            enrollmentRepository.save(enrollment);
            course.get().setCapacity(course.get().getCapacity() - 1);
            courseRepository.save(course.get());
        });
    }
}
