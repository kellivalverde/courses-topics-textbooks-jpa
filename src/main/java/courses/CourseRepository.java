package courses;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Collection<Course> findByTopicsContains(Topic topic); //place holders
	Collection<Course> findByTopicsId(Long id);
	Collection<Course> findByTextbooksContains(Textbook textbook);

}
