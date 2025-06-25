package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    List<StudentAnswer> findByUser_UserIdAndQuestion_QuestionMaster_QuestionMasterId(Long userId, Long paperId);
}