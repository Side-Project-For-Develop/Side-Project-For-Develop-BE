package com.h10.sideproject.poll.service;

import com.h10.sideproject.Result.dto.ResultResponseDto;
import com.h10.sideproject.Result.entity.Result;
import com.h10.sideproject.Result.repository.ResultRepository;
import com.h10.sideproject.category.entity.Category;
import com.h10.sideproject.category.mapper.CatrgoryMapper;
import com.h10.sideproject.category.repository.CategoryRepository;
import com.h10.sideproject.comment.repository.CommentRepository;
import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.common.response.ErrorCode;
import com.h10.sideproject.common.response.MessageCode;
import com.h10.sideproject.common.response.ResponseMessage;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.poll.dto.PollRequestDto;
import com.h10.sideproject.poll.dto.PollResponseDto;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.mapper.PollMapper;
import com.h10.sideproject.poll.repository.PollRepository;
import com.h10.sideproject.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final CategoryRepository categoryRepository;
    private final ResultRepository resultRepository;
    private final PollMapper pollMapper;
    private final CatrgoryMapper catrgoryMapper;
    private final CommentRepository commentRepository;
    @Transactional
    public ResponseMessage<?> createPoll(PollRequestDto pollRequestDto, Member member) {
        Category category = categoryRepository.findByName(pollRequestDto.getCategory()).orElse(null);

        if(category == null){
            category = catrgoryMapper.toCatrgory(pollRequestDto);
            categoryRepository.save(category);
        }
        Poll poll = pollMapper.toPoll(pollRequestDto,category,member);
        pollRepository.save(poll);
        return new ResponseMessage<>(MessageCode.POLL_WRITE_SUCCESS,null);
    }

    @Transactional
    public ResponseMessage<?> readPoll(Long poll_id, MemberDetailsImpl memberDetails) {
        Poll poll = pollRepository.findById(poll_id).orElseThrow(() -> new CustomException(ErrorCode.POLL_NOT_FOUND));
        poll.plusView();

        Boolean check =  memberDetails != null && resultRepository.existsByPollAndMember(poll, memberDetails.getMember());

        Double total = resultRepository.countAllByPoll(poll);
        Double count1 = resultRepository.countAllByPollAndChoice(poll,"choice1");
        Double count2 = resultRepository.countAllByPollAndChoice(poll,"choice2");

        String percent1 = String.format("%.2f",count1/total*100);
        String percent2 = String.format("%.2f",count2/total*100);

        //현재는 소수점 2자리까지 문자열 만들어 응답을 보내주지만 숫자로 보내줘야 할때를 대비해서 숫자도 만들어 놓음
        Double d1 = Double.parseDouble(percent1);
        Double d2 = Double.parseDouble(percent2);

        Optional<Result> result = resultRepository.findByPollAndMember(poll, memberDetails.getMember());

        if(result.isEmpty()){
            PollResponseDto pollResponseDto = pollMapper.toPollResponseDto(poll,check,percent1,percent2);
            return new ResponseMessage<>(MessageCode.POLL_READ_SUCCESS,pollResponseDto);
        }else {
            String myChoice = result.get().getChoice();
            ResultResponseDto resultResponseDto = pollMapper.toResultResponseDto(poll,check,percent1,percent2, myChoice);
            return new ResponseMessage<>(MessageCode.POLL_READ_SUCCESS,resultResponseDto);
        }
    }

    @Transactional
    public ResponseMessage<?>updatePoll(PollRequestDto pollRequestDto, Long poll_id, Member member){
        Poll poll = pollRepository.findById(poll_id).orElseThrow(() -> new CustomException(ErrorCode.POLL_NOT_FOUND));

        if(poll.getMember().getId() == member.getId()){
            Category category = categoryRepository.findByName(pollRequestDto.getCategory()).orElse(null);
            if(category == null){
                category = catrgoryMapper.toCatrgory(pollRequestDto);
                categoryRepository.save(category);
            }
            poll.update(pollRequestDto,category);
            return new ResponseMessage<>(MessageCode.POLL_UPDATE_SUCCESS,null);
        }else{
            return new ResponseMessage<>(ErrorCode.POLL_NOT_PERMISSION);
        }
    }

    @Transactional
    public ResponseMessage<?> deletePoll(Long poll_id, Member member) {
        Poll poll = pollRepository.findById(poll_id).orElseThrow(() -> new CustomException(ErrorCode.POLL_NOT_FOUND));
        if(poll.getMember().getId() == member.getId()){
            commentRepository.deleteAllByCommentId(poll_id);
            pollRepository.deleteById(poll_id);
            return new ResponseMessage<>(MessageCode.POLL_DELETE_SUCCESS,null);
        }else{
            return new ResponseMessage<>(ErrorCode.POLL_NOT_PERMISSION);
        }
    }

    public ResponseMessage<?> toks(MemberDetailsImpl memberDetails) {
        List<Poll> randomList;
        if(memberDetails == null){
            randomList = pollRepository.findAll();
        }else{
            randomList = pollRepository.findAllByMemberNot(memberDetails.getMember());

        }
        if(randomList.size() != 0){
            int idx = (int)(Math.random()*randomList.size());
            Poll poll = randomList.get(idx);
            poll.plusView();

            Boolean check = memberDetails != null && resultRepository.existsByPollAndMember(poll, memberDetails.getMember());

            Double total = resultRepository.countAllByPoll(poll);
            Double count1 = resultRepository.countAllByPollAndChoice(poll,"choice1");
            Double count2 = resultRepository.countAllByPollAndChoice(poll,"choice2");

            String cal1 = String.format("%.2f",count1/total*100);
            String cal2 = String.format("%.2f",count2/total*100);

            Optional<Result> result = null;
            if(memberDetails != null){
                result = resultRepository.findByPollAndMember(poll, memberDetails.getMember());
            }
            if(result.isEmpty()){
                PollResponseDto pollResponseDto = pollMapper.toPollResponseDto(poll,check,cal1,cal2);
                return new ResponseMessage<>(MessageCode.TOKS_READ_SUCCESS,pollResponseDto);
            }else {
                String myChoice = result.get().getChoice();
                ResultResponseDto resultResponseDto = pollMapper.toResultResponseDto(poll,check,cal1,cal2,myChoice);
                return new ResponseMessage<>(MessageCode.POLL_READ_SUCCESS,resultResponseDto);
            }
        }else {
            return new ResponseMessage<>(ErrorCode.TOKS_NOT_FOUND);
        }
    }
}
