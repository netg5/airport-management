/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.authservice;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Sergei Visotsky, 2018
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtClaimsVerifierIntegrationTest {

    @Autowired
    private JwtTokenStore tokenStore;

    @Test
    public void whenTokenDontContainIssuer_thenSuccess() {
        final String tokenValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDQ2NjcwMjQsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzYzN2MxY2EtYWM2My00ZGVlLWI2NDItYjJiMTcwNGEzODNiIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdfQ.1E5mMPk4zOnaI-P2AYSToobsh9wTNeP0PkCOGd4DZsg";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test
    public void whenTokenContainValidIssuer_thenSuccess() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODEiLCJleHAiOjE1MDQ3NDA3NDQsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzYzN2MxY2EtYWM2My00ZGVlLWI2NDItYjJiMTcwNGEzODNiIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJpYXQiOjE1MDQ3MzcxNDR9.G3vVR314v5bKiMJow0wRE0ZOXSakoRLxBSM9_PZeMms";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenContainInvalidIssuer_thenException() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODIiLCJleHAiOjE1MDQ3NDA2NTksInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzYzN2MxY2EtYWM2My00ZGVlLWI2NDItYjJiMTcwNGEzODNiIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJpYXQiOjE1MDQ3MzcwNTl9.60HxX5m0vpP6jfxpLPQWr_a5qMLk6owfknbYmBqb68g";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenDontContainUsername_thenException() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDQ3NDA3ODEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjNjM3YzFjYS1hYzYzLTRkZWUtYjY0Mi1iMmIxNzA0YTM4M2IiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sImlhdCI6MTUwNDczNzE4MX0.SEX15_d49_YOMw1UAPvh9pnPBKnATJUY-wN8r9kSVxA";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenContainEmptyUsername_thenException() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDQ3NDA5MjEsInVzZXJfbmFtZSI6IiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjNjM3YzFjYS1hYzYzLTRkZWUtYjY0Mi1iMmIxNzA0YTM4M2IiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sImlhdCI6MTUwNDczNzMyMX0.MM1RkBy90rTaDkCGGP1j9mKfSNcoRcHEa8WLC7-zR6A";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }
}