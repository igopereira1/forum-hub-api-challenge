package com.forumhub.api.controller;

import com.forumhub.api.dto.course.CourseCreateDTO;
import com.forumhub.api.dto.course.CourseUpdateDTO;
import com.forumhub.api.model.Course;
import com.forumhub.api.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseCreateDTO courseCreateDTO, UriComponentsBuilder uriBuilder) {
        Course createdCourse = courseService.createCourse(courseCreateDTO);
        return ResponseEntity.created(uriBuilder.path("/api/courses/{id}").buildAndExpand(createdCourse.getId()).toUri())
                .body(createdCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseUpdateDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
}