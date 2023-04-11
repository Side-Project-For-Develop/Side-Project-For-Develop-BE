package com.h10.sideproject.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h10.sideproject.common.exception.CustomException;
import com.h10.sideproject.member.dto.KakaoInfoDto;
import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.repository.MemberRepository;
import com.h10.sideproject.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.h10.sideproject.common.response.ErrorCode.NOT_FOUND_KAKAOID;


@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public String kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoInfoDto kakaoInfoDto = getKakaoMemberInfo(accessToken);

        // 3. 필요시에 회원가입
        Member member = joinKakao(kakaoInfoDto);

        // 4. JWT 토큰 반환
        String createToken =  jwtUtil.createToken(member.getEmail());

        return createToken;
    }
    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성 / HTTP 요청에서 사용될 헤더 정보를 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성 / 토큰값 받음
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "cfc1de46ec6e768d40702a0986c8af44");
        body.add("redirect_uri", "http://localhost:8080/api/member/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        //HTTP 요청을 보내기 위해 사용
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보"
    private KakaoInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoMemberRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoMemberRequest,
                String.class
        );

        String responseBody = response.getBody();
        log.info("responseBody" + responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String profileImage = jsonNode.get("profile")
                .get("profileImage").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email + ", " + profileImage);
        return new KakaoInfoDto(id, nickname, email, profileImage);
    }
    // 3. 필요시에 회원가입
    //KakaoInfoDto 는 카카오 api 에서 받는 카카오 사용자 정보담는 dto
    private Member joinKakao(KakaoInfoDto kakaoInfoDto) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        //카카오 사용자 정보에서 id
        Long kakaoId = kakaoInfoDto.getKakaoId();
        //kakaoMember 찾기
        Member kakaoMember = memberRepository.findById(kakaoId).orElseThrow(()->new CustomException(NOT_FOUND_KAKAOID));
        //카카오 api 에서 받은 email(카카오 email)
        String kakaoEmail = kakaoInfoDto.getEmail();
        //memberRepository 에 저장된 유저 email
        Member emailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
        if(kakaoMember == null) {
            // --------신규 회원가입---------
            //이메일
            String email = kakaoInfoDto.getEmail();
            //넥네임
            String nickname = kakaoInfoDto.getNickname();
            //비밀번호
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            //프로필 이미지
            String profileImage = kakaoInfoDto.getProfileImage();

            kakaoMember = new Member(email, encodedPassword, nickname, profileImage);
        }

        memberRepository.save(kakaoMember);
        return kakaoMember;
    }
}
