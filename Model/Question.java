package com.grd.online.paper.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "question_master_id")
    private QuestionMaster questionMaster;

    @Column(length = 1000)
    private String questionText;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctOption;

    private Integer marks;
}
