package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuestionMaster_QuestionMasterId(Long paperId);
}
