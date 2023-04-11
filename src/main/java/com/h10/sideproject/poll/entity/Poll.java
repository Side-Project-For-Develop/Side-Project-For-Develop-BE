package com.h10.sideproject.poll.entity;

import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.common.Timestamped;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.dto.PollRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Poll extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poll_id;

    @Column(nullable = false)
    private String title; //설문 제목

    @Column(nullable = false)
    @NotNull(message = "첫번째 선택지는 null이 될수없음")
    private String choice1; //첫번째 선택지

    @Column()
    private String choice1_img; //첫번째 선택지 이미지
    @Column(nullable = false)
    private String choice2; //두번째 선택지

    @Column()
    private String choice2_img; //두번째 선택지 이미지

    @Column()
    private Long view;  //조회수

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category; //카테고리

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //멤버

    @OneToMany(mappedBy="poll", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> PollResultList;

    public void update(PollRequestDto pollRequestDto,Category category){
        this.title = pollRequestDto.getTitle();
        this.category = category;
        this.choice1 = pollRequestDto.getChoice1();
        this.choice1_img = pollRequestDto.getChoice1_img();
        this.choice2 = pollRequestDto.getChoice2();
        this.choice2_img = pollRequestDto.getChoice2_img();
    }

    public void plusView(){
        this.view++;
    }

}
