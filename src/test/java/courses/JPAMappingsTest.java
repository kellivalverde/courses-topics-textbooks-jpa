package courses;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import courses.models.Course;
import courses.models.Textbook;
import courses.models.Topic;
import courses.repositories.CourseRepository;
import courses.repositories.TextbookRepository;
import courses.repositories.TopicRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager; // common practice

	 // injecting repositories
	//create Interfaces with CRUD repository added 
	@Resource
	private TopicRepository topicRepo;

	@Resource
	private CourseRepository courseRepo; 
	
	@Resource
	private TextbookRepository textbookRepo;

	
	
	
	@Test
	public void shouldSaveAndLoadTopic() {
		Topic topic = topicRepo.save(new Topic("topic"));// pulls Topic entity from topic repository -->CRUD Repo saves
		long topicId = topic.getId();

		entityManager.flush(); // forces JPA to hit the database when we try to find it
		entityManager.clear();

		Optional<Topic> result = topicRepo.findById(topicId);
		topic = result.get();
		assertEquals(topic.getName(), "topic");
	}

	@Test
	public void shouldGenerateTopicId() {
		Topic topic = topicRepo.save(new Topic("topic")); // shortcut version
		long topicId = topic.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(topicId, is(greaterThan(0L)));
	}

	@Test
	public void shouldSaveAndLoadCourse() {
		Course course = new Course("course name", "description"); // longer version
		course = courseRepo.save(course);
		long courseId = course.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();
		assertEquals(course.getName(), "course name");
	}

	@Test
	public void shouldEstablishCourseToTopicRelationships() {
		// one to many: Course to topic
		// topic is not the owner -- course is -- so make these topics first

		Topic java = topicRepo.save(new Topic("Java"));
		Topic ruby = topicRepo.save(new Topic("Ruby"));

		Course course = new Course("OO Languages", "description", java, ruby);
		course = courseRepo.save(course);
		long courseId = course.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Course> result = courseRepo.findById(courseId);
		course = result.get();

		assertThat(course.getTopics(), containsInAnyOrder(java, ruby));
		// initially throws error --> unexpected iterable over...? need to "Generate
		// hashCode() and equals() in each entity

	}

	// query
	@Test
	public void shouldFindCourseForTopic() {
		Topic java = topicRepo.save(new Topic("java"));
		// Topic ruby = topicRepo.save(new Topic("Ruby"));

		Course ooLanguages = courseRepo.save(new Course("OO Languages", "description", java));
		Course advancedJava = courseRepo.save(new Course("Adv Java", "Description", java));
		// Course advancedRuby = courseRepo.save(new Course("Adv Ruby", "Description",
		// ruby));

		entityManager.flush();
		entityManager.clear();

		Collection<Course> coursesForTopic = courseRepo.findByTopicsContains(java);
		// tied to our Topics Collection
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedJava));
	}

	// query
	@Test
	public void shouldFindCoursesForTopicById() {
		Topic ruby = topicRepo.save(new Topic("Ruby"));
		long topicId = ruby.getId(); // access that specific topic

		Course ooLanguages = courseRepo.save(new Course("OO Languages", "description", ruby));
		Course advancedRuby = courseRepo.save(new Course("Adv Ruby", "Description", ruby));

		entityManager.flush();
		entityManager.clear();

		Collection<Course> coursesForTopic = courseRepo.findByTopicsId(topicId); // own query - not CRUD's
		assertThat(coursesForTopic, containsInAnyOrder(ooLanguages, advancedRuby));

	}

	@Test
	public void shouldEstablishTextbookToCourseRelationship() {
		// Textbook owns Course --> make course first
		Course course = new Course("name", "description");
		courseRepo.save(course);
		long courseId = course.getId();
	
		Textbook book = new Textbook("title", course);
		textbookRepo.save(book);
		
		Textbook book2 = new Textbook("title two", course);
		textbookRepo.save(book2);
		
		
		entityManager.flush();
		entityManager.clear();

		//Many to One - many courses could use one book
		Optional<Course>result = courseRepo.findById(courseId);
		course = result.get();

		assertThat(course.getTextbooks(), containsInAnyOrder(book, book2));
		
	}

	@Test
	public void shouldSortCourses() {
		Course ooLanguages = new Course("OO Languages", "description");
		ooLanguages = courseRepo.save(ooLanguages);
				
		Course scriptingLanguages = new Course("Scripting Languages", "description");
		scriptingLanguages = courseRepo.save(scriptingLanguages);
		
		entityManager.flush();
		entityManager.clear();
		
		Collection<Course> sortedCourses = courseRepo.findAllByOrderByNameAsc();
		assertThat(sortedCourses, contains(ooLanguages, scriptingLanguages));
	}
	
}
