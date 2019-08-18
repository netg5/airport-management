package org.sergei.auth;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Sergei Visotsky
 */
public class PasswordEncoderTest {

    @Test
    public void encodePasswordTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("uCvtFPbxBEmVho9X"));
    }
}
