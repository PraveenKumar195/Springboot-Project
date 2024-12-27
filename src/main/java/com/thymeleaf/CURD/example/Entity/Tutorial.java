package com.thymeleaf.CURD.example.Entity;

//import org.hibernate.annotations.Columns;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Tutorial {

	
	@Id
	//@GeneratedValue(strategy= GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TUTORIAL_SEQ")
	@SequenceGenerator(name = "TUTORIAL_SEQ", sequenceName = "TUTORIAL_SEQ", allocationSize = 1)
	private Long id;
	
	private String title;
	
	private String description;
	
	private int grade;
	
	@Column(name ="published")
	private boolean published;
	
	public Tutorial() {
		
	}

	public Tutorial(String title, String description, int grade, boolean published) {
	
		this.title = title;
		this.description = description;
		this.grade = grade;
		this.published = published;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", title=" + title + ", description=" + description + ", grade=" + grade
				+ ", published=" + published + "]";
	}
	
	
	
}
