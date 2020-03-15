package courses.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import courses.models.Course;
import courses.models.Textbook;
import courses.models.Topic;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Collection<Course> findByTopicsContains(Topic topic); //place holders
	Collection<Course> findByTopicsId(Long id);
	Collection<Course> findByTextbooksContains(Textbook textbook);
	Course findByName(String courseName);
	Collection<Course> findAllByOrderByNameAsc();

}
