package com.example.demo384test.repository;

import com.example.demo384test.model.post.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository  extends JpaRepository<Question, Long> {
}
