package com.h10.sideproject.poll.service;

import com.h10.sideproject.Result.repository.ResultRepository;
import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.category.mapper.CatrgoryMapper;
import com.h10.sideproject.category.repository.CategoryRepository;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.dto.PollResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.mapper.PollMapper;
import com.h10.sideproject.poll.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {
    private final MemberRepository memberRepository;

    private final PollRepository pollRepository;

    private final CategoryRepository categoryRepository;
    private final ResultRepository resultRepository;
    private final PollMapper pollMapper;
    private final CatrgoryMapper catrgoryMapper;
    @Transactional()
    public ResponseMessage<?> createPoll(PollRequestDto pollRequestDto, Member member) {
        Category category = categoryRepository.findByName(pollRequestDto.getCategory()).orElse(null);

        if(category == null){
            category = catrgoryMapper.toCatrgory(pollRequestDto);
        }

        Poll poll = pollMapper.toPoll(pollRequestDto,category,member);
        try {
            categoryRepository.save(category);
            pollRepository.save(poll);
        }catch (Exception exception){
            throw new CustomException(ErrorCode.POLL_REQUIRED_NOT_ENOUGH);
        }
        return new ResponseMessage<>(MessageCode.POLL_WRITE_SUCCESS,null);
    }

    @Transactional
    public ResponseMessage<?> readPoll(Long poll_id, Member member) {
        Poll poll = pollRepository.findById(poll_id).orElseThrow(() -> new CustomException(ErrorCode.POLL_NOT_FOUND));
        poll.plusView();

        Boolean check = resultRepository.existsByPollAndMember(poll,member);

        Double total = resultRepository.countAllByPoll(poll);
        Double count1 = resultRepository.countAllByPollAndChoice(poll,"choice1");
        Double count2 = resultRepository.countAllByPollAndChoice(poll,"choice2");

        String percent1 = String.format("%.2f",count1/total*100);
        String percent2 = String.format("%.2f",count2/total*100);

        Double d1 = Double.parseDouble(percent1);
        Double d2 = Double.parseDouble(percent2);

        System.out.println("cal1 = " + percent1);
        System.out.println("d1 = " + d1);
        System.out.println();
        System.out.println("cal2 = " + percent2);
        System.out.println("d2 = " + d2);

        PollResponseDto pollResponseDto = pollMapper.toPollResponseDto(poll,check,percent1,percent2);
        return new ResponseMessage<>(MessageCode.POLL_READ_SUCCESS,pollResponseDto);
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


    public ResponseEntity<?> toks(UserDetails user) {
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        List<Poll> randomList = pollRepository.findAllByMemberNot(member);
        int idx = (int)(Math.random()*randomList.size());
        Poll poll = randomList.get(idx);
        poll.plusView();

        Boolean check = resultRepository.existsByPollAndMember(poll,member);

        Double total = resultRepository.countAllByPoll(poll);
        Double count1 = resultRepository.countAllByPollAndChoice(poll,"choice1");
        Double count2 = resultRepository.countAllByPollAndChoice(poll,"choice2");

        String cal1 = String.format("%.2f",count1/total*100);
        String cal2 = String.format("%.2f",count2/total*100);

        Double d1 = Double.parseDouble(cal1);
        Double d2 = Double.parseDouble(cal2);

        PollResponseDto pollResponseDto = PollResponseDto.builder()
                .nickname(poll.getMember().getNickname())
                .category(poll.getCategory().getName())
                .title(poll.getTitle())
                .choice1(poll.getChoice1())
                .choice1_img(poll.getChoice1_img())
                .choice2(poll.getChoice2())
                .choice2_img(poll.getChoice2_img())
                .view(poll.getView())
                .vote(check)
                .choice1_result(cal1)
                .choice2_result(cal2)
                .build();

        return new ResponseEntity<>(pollResponseDto,HttpStatus.OK);
    }
}
