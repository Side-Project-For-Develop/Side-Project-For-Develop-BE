package com.h10.sideproject.security;

import com.h10.sideproject.member.entity.Member;
import com.h10.sideproject.member.mapper.MemberMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberDetailsImpl implements UserDetails{

    private final Member member;
    private final String memberId;

    public MemberDetailsImpl(Member member, String memberId) {
        this.member = member;
        this.memberId = memberId;
    }

    public Long getId() {
        return this.member.getId();
    }

    public Member getMember() {
        return this.member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
