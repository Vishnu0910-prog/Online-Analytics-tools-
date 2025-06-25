package com.grd.online.paper.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionMasterId;

    private String title;

    private Long duration;

    private Integer totalMarks;

    @Column(nullable = true)
    private String remarks;

    @Column(nullable = true)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserModel createdBy;

    private Date createdDate = new Date();

}
