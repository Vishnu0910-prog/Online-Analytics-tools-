package com.grd.online.paper.Repository;

import com.grd.online.paper.Model.QuestionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMasterRepository extends JpaRepository<QuestionMaster, Long> {
    List<QuestionMaster> findByIsActiveTrue();
}