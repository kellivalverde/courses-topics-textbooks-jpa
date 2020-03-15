package courses.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import courses.CourseNotFoundException;
import courses.TextbookNotFoundException;
import courses.TopicNotFoundException;
import courses.models.Course;
import courses.models.Textbook;
import courses.models.Topic;
import courses.repositories.CourseRepository;
import courses.repositories.TextbookRepository;
import courses.repositories.TopicRepository;

@Controller
public class CourseController {

	@Resource
	CourseRepository courseRepo;
	@Resource
	TopicRepository topicRepo;
	@Resource
	TextbookRepository textbookRepo;

	@RequestMapping("/courses/{id}")
	public String findOneCourse(@PathVariable(value = "id") long id, Model model) throws CourseNotFoundException {
		Optional<Course> course = courseRepo.findById(id);
		// have to handle if it is or is not present

		if (course.isPresent()) {
			model.addAttribute("courseModel", course.get()); // why plural?
			return "course"; // template for single course
		}
		throw new CourseNotFoundException();
	}

	@RequestMapping("/courses") // end-point
	public String findAllCourses(Model model) {
		model.addAttribute("coursesModel", courseRepo.findAll()); // model we are referencing
		return ("courses"); // template -> does not have to match the end-point
	}

	@RequestMapping("/topic/{id}")
	public String findOneTopic(@PathVariable(value = "id") long id, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			model.addAttribute("topicModel", topic.get()); // why plural?
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));

			return "topic"; // template for single course
		}
		throw new TopicNotFoundException();
	}

	@RequestMapping("/topics") // end-point
	public String findAllTopics(Model model) {
		model.addAttribute("topicsModel", topicRepo.findAll());
		return ("topics");
	}

	@RequestMapping("/textbook")
	public String findOneTextbook(@RequestParam(value = "id") long id, Model model) throws TextbookNotFoundException {
		Optional<Textbook> textbook = textbookRepo.findById(id);

		if (textbook.isPresent()) {
			model.addAttribute("textbooks", textbook.get()); // why plural?
			model.addAttribute("courses", courseRepo.findByTextbooksContains(textbook.get()));

			return "textbook"; // template for single course
		}
		throw new TextbookNotFoundException();
	}

	@RequestMapping("/textbooks")
	public String findAllTextbooks(Model model) {
		model.addAttribute("textbooks", textbookRepo.findAll());
		return ("textbooks");

	}

	@RequestMapping("/add-course")
	public String addCourse(String courseName, String courseDescription, String topicName) {
		Topic topic = topicRepo.findByName(topicName);
		if (topic == null) {
			topic = new Topic(topicName);
			topicRepo.save(topic); // dynamically saving a topic
		}

		Course newCourse = courseRepo.findByName(courseName);

		if (newCourse == null) {
			newCourse = new Course(courseName, courseDescription, topic);
			courseRepo.save(newCourse); // dynamically saving a Course
		}

		return "redirect:/courses";
	}

	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {

		Course foundCourse = courseRepo.findByName(courseName);

		if (foundCourse != null) {

			for (Textbook text : foundCourse.getTextbooks()) {
				textbookRepo.delete(text);
			} //deletes book before deleting course

			courseRepo.delete(foundCourse);
		}

		return "redirect:/courses";
	}

	@RequestMapping("/courses/del-course")
	public String deleteCourseById(Long courseId) {

		Optional<Course> foundCourseResult = courseRepo.findById(courseId);
		Course courseToRemove = foundCourseResult.get();
		
		for (Textbook text : courseToRemove.getTextbooks()) {
			textbookRepo.delete(text);
		}
		
		
		courseRepo.deleteById(courseId);

		return "redirect:/courses";

	}

	@RequestMapping("/find-by-topic")
	public String findCoursesByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("courses", courseRepo.findByTopicsContains(topic));

		return "/topic";

	}

	@RequestMapping("/sort-courses")
	public String sortCourses(Model model) {

		model.addAttribute("coursesModel", courseRepo.findAllByOrderByNameAsc());

		return "/courses";

	}

	@RequestMapping(path="/topics/{topicName}", method=RequestMethod.POST)
	public String addTopic(@PathVariable String topicName, Model model) {
		Topic topicToAdd = topicRepo.findByName(topicName)
;
		if(topicToAdd == null) {
			topicToAdd = new Topic(topicName);
			topicRepo.save(topicToAdd);
		}
		model.addAttribute("topicsModel", topicRepo.findAll());
		return "partials/topics-list-added";  //talks to our partial
}
	
	
}