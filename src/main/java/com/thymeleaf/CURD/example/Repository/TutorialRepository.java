package com.thymeleaf.CURD.example.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thymeleaf.CURD.example.Entity.Tutorial;



@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	List<Tutorial> findByTitleContainingIgnoreCase(String keyword);
	
	@Query("UPDATE Tutorial t SET t.published = :published WHERE t.id = :id")
	@Modifying
	public void updatePublishedStatus( @Param("id") Long id, @Param("published") boolean published );
	
}
