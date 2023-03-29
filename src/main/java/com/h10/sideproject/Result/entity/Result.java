package com.h10.sideproject.Result.entity;

import com.h10.sideproject.Result.dto.ResultRequestDto;
import com.h10.sideproject.common.Timestamped;
import com.h10.sideproject.poll.entity.Poll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Result extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long result_id;

    @Column()
    private String choice; //선택지

    @JoinColumn(name = "poll_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll; //설문

    public void update(ResultRequestDto resultRequestDto){
        this.choice = resultRequestDto.getChoice();
    }
}
