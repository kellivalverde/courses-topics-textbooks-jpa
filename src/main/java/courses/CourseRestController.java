package courses;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CourseRestController {

	@Resource
	private CourseRepository courseRepo;
	
	@RequestMapping("/api/courses")  //REST pattern - -add to all my REST end-points
	public Iterable<Course> findAllCourses(){
		return courseRepo.findAll();
	}
	
	
	
	
	
}
