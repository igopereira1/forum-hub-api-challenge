package com.forumhub.api.service;

import com.forumhub.api.dto.course.CourseCreateDTO;
import com.forumhub.api.dto.course.CourseUpdateDTO;
import com.forumhub.api.model.Course;
import com.forumhub.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course createCourse(CourseCreateDTO courseCreateDTO) {
        Course course = new Course();
        course.setName(courseCreateDTO.name());
        course.setCategory(courseCreateDTO.category());
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
    }

    @Transactional
    public Course updateCourse(Long id, CourseUpdateDTO courseUpdateDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));

        // Update course properties
        course.setName(courseUpdateDTO.name());
        course.setCategory(courseUpdateDTO.category());

        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));

        courseRepository.delete(course);
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
