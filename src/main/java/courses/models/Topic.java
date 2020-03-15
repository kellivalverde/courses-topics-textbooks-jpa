package courses.models;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private long id; // primary key --> made by JPA
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "topics") // because topics is not the owner
	private Collection<Course> courses;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<Course> getCourses() {
		return courses;
	}

	// default constructor required by JPA
	public Topic() {

	}

	public Topic(String name) {
		this.name = name;
	}
	// Source -> Generate hashCode() and equals()
	// JPA needs this so it knows how to assign the id --> must to for every entity

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
