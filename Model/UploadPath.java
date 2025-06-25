package com.grd.online.paper.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @ManyToOne
    @JoinColumn(name = "question_master_id")

    @Builder.Default
    private QuestionMaster questionMaster = null;

    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();
}
