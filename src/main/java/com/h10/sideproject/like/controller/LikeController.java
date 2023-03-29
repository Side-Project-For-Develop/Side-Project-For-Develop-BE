package com.h10.sideproject.like.controller;

import com.h10.sideproject.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;
    @PostMapping("/like/{poll_id}")
    public ResponseEntity<?> createLike(@PathVariable Long poll_id, @AuthenticationPrincipal UserDetails user) {
        return likeService.createLike(poll_id,user);
    }
}
