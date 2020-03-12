package courses;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class CourseControllerTest {

	@InjectMocks // gives us access to our controller
	private CourseController underTest;

	// mocking populator
	@Mock
	private Course course;
	long courseId;
	@Mock
	private Course course2;
	@Mock
	private CourseRepository courseRepo;

	@Mock
	private Topic topic;
	@Mock
	private Topic topic2;
	@Mock
	private TopicRepository topicRepo;

	@Mock
	private Textbook textbook;
	@Mock
	private Textbook textbook2;
	@Mock
	private TextbookRepository textbookRepo;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddSingleCourseToModel() throws CourseNotFoundException {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));

		underTest.findOneCourse(arbitraryCourseId, model);
		verify(model).addAttribute("courseModel", course); // why plural?

	}

	@Test
	public void shouldAddAllCoursesToModel() {
		Collection<Course> allCourses = Arrays.asList(course, course2);
		when(courseRepo.findAll()).thenReturn(allCourses); // methods from CRUD Repo

		underTest.findAllCourses(model);
		verify(model).addAttribute("coursesModel", allCourses);
	}

	@Test
	public void shouldAddSingleTopicToModel() throws TopicNotFoundException {
		long arbitraryTopicId = 1;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));

		underTest.findOneTopic(arbitraryTopicId, model);
		verify(model).addAttribute("topics", topic); // why plural?

	}

	@Test
	public void shouldAddAllTopicsToModel() {
		Collection<Topic> allTopics = Arrays.asList(topic, topic2);
		when(topicRepo.findAll()).thenReturn(allTopics); // methods from CRUD Repo

		underTest.findAllTopics(model);
		verify(model).addAttribute("topics", allTopics);
	}
//textbooks	

	@Test
	public void shouldAddSingleTextbookToModel() throws TextbookNotFoundException {
		long arbitraryTextbookId = 1;
		when(textbookRepo.findById(arbitraryTextbookId)).thenReturn(Optional.of(textbook));

		underTest.findOneTextbook(arbitraryTextbookId, model);
		verify(model).addAttribute("textbooks", textbook); // why plural?

	}

	@Test
	public void shouldAddAllTextbooksToModel() {
		Collection<Textbook> allBooks = Arrays.asList(textbook, textbook2);
		when(textbookRepo.findAll()).thenReturn(allBooks); // methods from CRUD Repo

		underTest.findAllTextbooks(model);
		verify(model).addAttribute("textbooks", allBooks);
	}

	@Test
	public void shouldAddAdditionalCoursesToModel() {
		String topicName = "topic name";
		Topic newTopic = topicRepo.findByName(topicName);

		String courseName = "new course";
		String courseDescription = "new course description";

		underTest.addCourse(courseName, courseDescription, topicName);
		Course newCourse = new Course(courseName, courseDescription, newTopic);

		when(courseRepo.save(newCourse)).thenReturn(newCourse);

	}

	@Test
	public void shouldRemoveACourseFromModel() {
		String courseName = course.getName();
		when(courseRepo.findByName(courseName)).thenReturn(course);

		underTest.deleteCourseByName(courseName);
		verify(courseRepo).delete(course);
	}

	@Test
	public void shouldRemoveCourseFromModelById() {	
		underTest.deleteCourseById(courseId);
		verify(courseRepo).deleteById(courseId);
	
	
	}

}
