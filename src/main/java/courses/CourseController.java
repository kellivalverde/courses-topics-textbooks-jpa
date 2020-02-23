package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

	@Resource
	CourseRepository courseRepo;
	@Resource
	TopicRepository topicRepo;

	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value = "id") long id, Model model) throws CourseNotFoundException {
		Optional<Course> course = courseRepo.findById(id);
		// have to handle if it is or is not present

		if (course.isPresent()) {
			model.addAttribute("courses", course.get());  //why plural?
			return "course"; //template for single course
		}
		throw new CourseNotFoundException();
	}

	
	
	@RequestMapping("/courses")// end-point
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll()); //model we are referencing
		return ("courses"); //template -> does not have to match the end-point
	}

	

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value = "id") long id, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(id);
		
		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get()); //why plural?
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			
			return "topic"; //template for single course
		}
		throw new TopicNotFoundException();
	}

	
	
	@RequestMapping("/topics")// end-point
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll()); 
		return ("topics");
	}
	
}