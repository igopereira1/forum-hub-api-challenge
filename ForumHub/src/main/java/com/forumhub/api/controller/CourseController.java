package com.forumhub.api.controller;

import com.forumhub.api.dto.course.CourseCreateDTO;
import com.forumhub.api.dto.course.CourseDetailsDTO;
import com.forumhub.api.dto.course.CourseUpdateDTO;
import com.forumhub.api.model.Course;
import com.forumhub.api.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity createCourse(@RequestBody @Valid CourseCreateDTO courseCreateDTO, UriComponentsBuilder uriBuilder) {
        CourseDetailsDTO createdCourse = courseService.createCourse(courseCreateDTO);
        var uri = uriBuilder.path("/api/courses/{id}").buildAndExpand(createdCourse.id()).toUri();
        return ResponseEntity.created(uri).body(createdCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO) {
        CourseDetailsDTO updatedCourse = courseService.updateCourse(id, courseUpdateDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAllCourses() {
        List<CourseDetailsDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
}