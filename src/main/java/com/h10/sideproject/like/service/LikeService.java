package com.h10.sideproject.like.service;

import com.h10.sideproject.like.entity.Likes;
import com.h10.sideproject.like.repository.LikeRepository;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.poll.entity.Poll;
import com.h10.sideproject.poll.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final MemberRepository memberRepository;
    private final PollRepository pollRepository;
    private final LikeRepository likeRepository;
    public ResponseEntity<?> createLike(Long poll_id, UserDetails user) {
        Poll poll = pollRepository.findById(poll_id).orElse(null);
        Member member = memberRepository.findByEmail(user.getUsername()).orElse(null);
        Likes like = likeRepository.findByPollAndAndMember(poll,member).orElse(null);
        if (like == null){
            likeRepository.save(
                Likes.builder()
                        .poll(poll)
                        .member(member)
                        .build()
            );
            return new ResponseEntity<>("좋아요 성공", HttpStatus.OK);
        }else{
            likeRepository.delete(like);
            return new ResponseEntity<>("좋아요 취소성공", HttpStatus.OK);
        }
    }
}
