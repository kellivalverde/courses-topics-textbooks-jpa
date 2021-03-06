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

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {

	@Resource
	private CourseRepository courseRepo;

	@Resource
	private TopicRepository topicRepo;

	@RequestMapping("") // REST pattern - -add to all my REST end-points
	public Iterable<Topic> findAllTopics() {
		return topicRepo.findAll();
	}
	@RequestMapping("/{id}")
	public Optional<Topic> findOneTopic(@PathVariable long id) {
		return topicRepo.findById(id);
	}

	@RequestMapping("/{topicName}/courses") // topicName is the path variable
	public Collection<Course> findAllCoursesByTopic(@PathVariable(value = "topicName") String topicName) {
		Topic topic = topicRepo.findByNameIgnoreCaseLike(topicName);
		return courseRepo.findByTopicsContains(topic);
	}

}

