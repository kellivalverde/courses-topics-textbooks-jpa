package courses.repositories;

import org.springframework.data.repository.CrudRepository;

import courses.models.Textbook;

public interface TextbookRepository extends CrudRepository<Textbook, Long> {

}
