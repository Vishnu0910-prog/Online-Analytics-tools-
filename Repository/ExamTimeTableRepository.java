package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.ExamTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamTimeTableRepository extends JpaRepository<ExamTimeTable, Long> {

    List<ExamTimeTable> findByExamEndTimeAfter(LocalDateTime examStartTime);

    List<ExamTimeTable> findByExamStartTimeLessThanEqualAndExamEndTimeGreaterThanEqual(LocalDateTime startDateTime,
            LocalDateTime endDateTime);

    List<ExamTimeTable> findByExamEndTimeGreaterThanEqual(LocalDateTime endDateTime);

}
