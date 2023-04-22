package com.courseenrollment.backend.repository;

import com.courseenrollment.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT * FROM course WHERE course_id NOT IN (SELECT course_id FROM enrollment WHERE user_id = ?1)" +
            "AND capacity > 0", nativeQuery = true)
    List<Course> findAllAvailableCoursesByUser(Integer userId);

    @Query(value = "SELECT * FROM course WHERE course_name LIKE ?1%", nativeQuery = true)
    List<Course> findCoursesBySearch(String name);

    @Query(value = "SELECT * FROM course WHERE capacity > 0", nativeQuery = true)
    List<Course> findAllAvailableCourses();
}
