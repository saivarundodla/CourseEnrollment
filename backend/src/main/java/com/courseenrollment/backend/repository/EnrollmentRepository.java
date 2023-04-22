package com.courseenrollment.backend.repository;

import com.courseenrollment.backend.entity.Enrollment;
import com.courseenrollment.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findAllByUser(Optional<User> oldUser);
}
