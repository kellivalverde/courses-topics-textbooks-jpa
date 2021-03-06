package courses.controllers;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import courses.models.Course;
import courses.models.Topic;
import courses.repositories.CourseRepository;
import courses.repositories.TopicRepository;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://127.0.0.1:5500") //"*/*" means everything
@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

	@Resource
	private CourseRepository courseRepo;

	@Resource
	private TopicRepository topicRepo;

	@RequestMapping("") // REST pattern - -add to all my REST end-points
	public Iterable<Course> findAllCourses() {
		return courseRepo.findAll();
	}

	@RequestMapping("/{id}")
	public Optional<Course> findOneCourse(@PathVariable long id) {
		return courseRepo.findById(id);
	}

	@RequestMapping("/topics/{topicName}") // topicName is the path variable
	public Collection<Course> findAllCoursesByTopic(@PathVariable(value = "topicName") String topicName) {
		Topic topic = topicRepo.findByNameIgnoreCaseLike(topicName);
		return courseRepo.findByTopicsContains(topic);
	}

}
