package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id") long id, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(id);

		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get()); // why plural?
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));

			return "topic"; // template for single course
		}
		throw new TopicNotFoundException();
	}

	@RequestMapping("/topics") // end-point
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
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
		Course newCourse = courseRepo.findByName(courseName);

		if (newCourse == null) {
			newCourse = new Course(courseName, courseDescription, topic);
			courseRepo.save(newCourse);
		}

		return "redirect:/courses";
	}

	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {
		if(courseRepo.findByName(courseName) != null) {
			Course deletedCourse = courseRepo.findByName(courseName);
			courseRepo.delete(deletedCourse);		
		}
		
		return "redirect:/courses";
	}

	@RequestMapping("/courses/del-course")
	public String deleteCourseById(Long courseId) {
	
		courseRepo.deleteById(courseId);
		
		return "redirect:/courses"; 
		
		//not working becasue There was an unexpected error (type=Bad Request, status=400).
		//Failed to convert value of type 'java.lang.String' to required type 'long'; nested exception is java.lang.NumberFormatException: For input string: "del-course"
			
	}
	
	
	@RequestMapping("/find-by-topic")
	public String findCoursesByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("coursesModel", courseRepo.findByTopicsContains(topic));

	return "/topic";
	
	}
	

	
	
	
	
}