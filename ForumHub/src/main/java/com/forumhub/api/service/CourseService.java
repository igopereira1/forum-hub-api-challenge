package com.forumhub.api.service;

import com.forumhub.api.dto.course.CourseCreateDTO;
import com.forumhub.api.dto.course.CourseDetailsDTO;
import com.forumhub.api.dto.course.CourseUpdateDTO;
import com.forumhub.api.model.Course;
import com.forumhub.api.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public CourseDetailsDTO createCourse(CourseCreateDTO courseCreateDTO) {
        Course course = new Course();
        course.setName(courseCreateDTO.name());
        course.setCategory(courseCreateDTO.category());

        course = courseRepository.save(course);

        return new CourseDetailsDTO(course);
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
    }

    @Transactional
    public CourseDetailsDTO updateCourse(Long id, CourseUpdateDTO courseUpdateDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        course.setName(courseUpdateDTO.name());
        course.setCategory(courseUpdateDTO.category());

        course = courseRepository.save(course);

        return new CourseDetailsDTO(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        courseRepository.delete(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDetailsDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(CourseDetailsDTO::new)
                .toList();
    }
}