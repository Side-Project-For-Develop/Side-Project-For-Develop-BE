package com.h10.sideproject.category.entity;

import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column(nullable = false)
    private String name; // 카테고리 이름

    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Poll> pollList;
}
