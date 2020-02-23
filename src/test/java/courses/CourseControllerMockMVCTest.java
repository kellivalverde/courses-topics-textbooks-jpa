package courses;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerMockMVCTest {

	@Resource
	private MockMvc mvc;

	@Mock
	private Course course;
	@Mock
	private Course course2;
	@MockBean
	private CourseRepository courseRepo;

	@Mock
	private Topic topic;
	@Mock
	private Topic topic2;
	@MockBean
	private TopicRepository topicRepo;

	@Mock
	private Textbook textbook;
	@Mock
	private Textbook textbook2;
	@MockBean
	private TextbookRepository textbookRepo;

	
	//***************  Courses  *******************
	@Test
	public void shouldRouteToSingleCourseView() throws Exception {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(view().name(is("course")));
	}

	@Test
	public void shouldBeOkForSingleCourse() throws Exception {
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkForSingleCourse() throws Exception {
		mvc.perform(get("/course?id=1")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPutSingleCourseIntoModel() throws Exception {
		when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
		mvc.perform(get("/course?id=1")).andExpect(model().attribute("courseModel", is(course)));
	}

	@Test
	public void shouldRouteToAllCourseView() throws Exception {
		mvc.perform(get("/courses")).andExpect(view().name(is("courses"))); // hits our template
	} // was "/show-courses" in previous demo

	@Test
	public void shouldBeOkForAllCourses() throws Exception {
		mvc.perform(get("/courses")).andExpect(status().isOk());
		}

	@Test
	public void shouldPutAllCoursesIntoModel() throws Exception {
		Collection<Course> allCourses = Arrays.asList(course, course2);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		mvc.perform(get("/courses")).andExpect(model().attribute("coursesModel", is(allCourses)));
	}
	
	//***************  Topics  *******************
	@Test
	public void shouldRouteToSingleTopicView() throws Exception {
		long arbitraryTopicId = 42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(view().name(is("topic")));
	}
	
	@Test
	public void shouldBeOkForSingleTopic() throws Exception {
		long arbitraryTopicId = 42;
		when(topicRepo.findById(arbitraryTopicId)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkForSingleTopic() throws Exception {
		mvc.perform(get("/topic?id=42")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleTopicIntoModel() throws Exception {
		when(topicRepo.findById(42L)).thenReturn(Optional.of(topic));
		mvc.perform(get("/topic?id=42")).andExpect(model().attribute("topics", is(topic)));
	}																// or model named "topicModel"
	
	@Test
	public void shouldRouteToAllTopicView() throws Exception {
		mvc.perform(get("/topics")).andExpect(view().name(is("topics"))); // hits our template
	} // was "/show-courses" in previous demo
	
	@Test
	public void shouldBeOkForAllTopics() throws Exception {
		mvc.perform(get("/topics")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutAllTopicsIntoModel() throws Exception {
		Collection<Topic> allTopics = Arrays.asList(topic, topic2);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		mvc.perform(get("/topics")).andExpect(model().attribute("topics", is(allTopics)));
	}															//"topicsModel"
	
	
	
	
	
	
	
	


	
	
	
	
	
}
