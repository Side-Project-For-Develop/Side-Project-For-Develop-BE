package com.h10.sideproject.poll.service;

import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.category.repository.CategoryRepository;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.dto.PollResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PollService {
    private final MemberRepository memberRepository;

    private final PollRepository pollRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<?> createPoll(PollRequestDto pollRequestDto, UserDetails user) {
        Category category = categoryRepository.findByName(pollRequestDto.getCategory()).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        if(category == null){
            category = categoryRepository.save(Category.builder().name(pollRequestDto.getCategory()).build());
        }
        pollRepository.save(
                Poll.builder()
                .title(pollRequestDto.getTitle())
                .category(category)
                .choice1(pollRequestDto.getChoice1())
                .choice1_img(pollRequestDto.getChoice1_img())
                .choice2(pollRequestDto.getChoice2())
                .choice2_img(pollRequestDto.getChoice2_img())
                .view(0L)
                .member(member)
                .build()
        );
        return new ResponseEntity<>("설문 작성 완료",HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?>updatePoll(PollRequestDto pollRequestDto, Long poll_id, UserDetails user) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        if(poll != null && poll.getMember().getId() == member.getId()){
            Category category = categoryRepository.findByName(pollRequestDto.getCategory()).orElse(null);
            if(category == null){
                category = categoryRepository.save(Category.builder().name(pollRequestDto.getCategory()).build());
            }
            poll.update(pollRequestDto,category);
            return new ResponseEntity<>("설문 수정 성공",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("권한 없음",HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deletePoll(Long poll_id, UserDetails user) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        if(poll != null && poll.getMember().getId() == member.getId()){
            pollRepository.deleteById(poll_id);
            return new ResponseEntity<>("설문 삭제 성공",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("권한 없음",HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> readPoll(Long poll_id) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        poll.plusView();
        PollResponseDto pollResponseDto = PollResponseDto.builder()
                .nickname(poll.getMember().getNickname())
                .title(poll.getTitle())
                .category(poll.getCategory().getName())
                .choice1(poll.getChoice1())
                .choice1_img(poll.getChoice1_img())
                .choice2(poll.getChoice2())
                .choice2_img(poll.getChoice2_img())
                .view(poll.getView())
                .vote(false)
                .build();
        return new ResponseEntity<>(pollResponseDto,HttpStatus.OK);
    }
}
