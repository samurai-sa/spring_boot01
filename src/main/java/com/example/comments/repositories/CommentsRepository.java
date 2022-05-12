package com.example.comments.repositories;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.comments.entities.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Integer> {
	// 一覧表示の降順
	public List<Comments> findAllByOrderByCreatedAtDesc();
}
