package com.example.demo.Service;

import com.example.demo.Entity.Users;
import com.example.demo.Entity.jwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JwtAuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private jwtTokenUtil jwtTokenUtil;
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException("用户名密码错误");
        }
        Users loginUser = (Users) authentication.getPrincipal(); // 生成token
        return jwtTokenUtil.generateToken(loginUser);
    }
}
