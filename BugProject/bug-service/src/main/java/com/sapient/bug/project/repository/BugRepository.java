package com.sapient.bug.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sapient.bug.project.models.Bug;
/**
 * 
 * @author Archna
 *
 */
@Repository
public interface BugRepository extends MongoRepository<Bug,String>{
	Page<Bug> findByProjectIdAndNameIgnoreCaseContaining( String projectId, String name, Pageable paging);
	Page<Bug> findByProjectIdContaining(String projectId, Pageable paging);
}
