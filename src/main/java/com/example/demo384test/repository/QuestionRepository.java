package com.example.demo384test.repository;

import com.example.demo384test.model.post.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface QuestionRepository  extends JpaRepository<Question, Long> {


    @Query(value = "SELECT * FROM questions WHERE id =?1", nativeQuery = true)
    Question findByID(Long id);

}
