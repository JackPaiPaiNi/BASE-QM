package com.skyworth.core.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.skyworth.core.user.vo.UserVo;

/**
 * <p>User: 魏诚
 * 不能使用@Service注解，测试发现@Value会直接取括号中的字符串
 */
@Component
public class PasswordHelper {

    @Value("${password.algorithmName}")
    private String algorithmName;
    
    @Value("${password.hashIterations}")
    private String hashIterations;

    public void encryptPassword(UserVo userVo) {

        String newPassword = new SimpleHash(
                algorithmName,
                userVo.getMm(),
                null,
                Integer.valueOf(hashIterations)).toHex();

        userVo.setMm(newPassword);
    }
}
