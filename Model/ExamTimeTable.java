package com.grd.online.paper.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamTimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeTableId;

    @ManyToOne
    @JoinColumn(name = "question_master_id")
    private QuestionMaster questionMaster;

    private LocalDateTime examStartTime;

    private LocalDateTime examEndTime;

    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel createdBy;
}
