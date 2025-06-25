package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByQuestionMaster_QuestionMasterId(Long paperId);

    Optional<Result> findByUser_UserIdAndQuestionMaster_QuestionMasterId(Long userId, Long questionMasterId);
}
