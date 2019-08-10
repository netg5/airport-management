package org.sergei.reservation.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.reservation.rest.dto.AuthTokenInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationServiceTest {

    @Autowired
    private ExchangeAuthService exchangeAuthService;

    @Test
    public void getTokenInfoTest() {
        AuthTokenInfoDTO tokenInfo = exchangeAuthService.sendTokenRequest();
        log.info(tokenInfo.toString());
    }

}
