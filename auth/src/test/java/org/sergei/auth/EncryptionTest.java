package org.sergei.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Sergei Visotsky
 */
@Slf4j
public class EncryptionTest {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void getEncodedSecret() {
        String pswdToEncode = "trusted-client-secret";
        String pswdEncoded = passwordEncoder.encode(pswdToEncode);
        log.info(pswdEncoded);
    }

}
