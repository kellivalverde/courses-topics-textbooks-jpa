package courses.repositories;

import org.springframework.data.repository.CrudRepository;

import courses.models.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {

	Topic findByName(String topicName);

	Topic findByNameIgnoreCaseLike(String topicName);

}
