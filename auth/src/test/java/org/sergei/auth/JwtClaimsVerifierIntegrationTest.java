/*
 * Copyright 2018-2019 the original author.
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

package org.sergei.auth;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class JwtClaimsVerifierIntegrationTest {

    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Test
    public void whenTokenDontContainIssuer_thenSuccess() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg2NzkyOTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDZhMGVlZS1jNWE3LTQzNjEtYmM3NC02YzdlMDJkYWQzOWEiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.arlt3b9lMKarlc39TBHofp4uCbmvWmAqaGh28aeX3RU2qtzlYplHXZek2zx3EA2ZBy08ld-Y0heuWZoWfLA8aiwEWhgCYI4ratMMe8h4_PJPJnQ8CulvGK5M6gvDL0gqu4xoMh9VM0SseNvWkG40x4dcfsLbJt9IWhQqIkO4_8f4Ozv8iYvhFb-I4UNWe_LWKB8i7VC1y6mPDKjK0oBrVGH43P6ZtNClsCU4TLcAfVB9UMqQNbEf04gKEMmD2r94Pk6nEXg2JZ95B62SmwaqMEZMGegzThIF35Ii5Y2Svo0chFExRuLsrW__Q5nClwXJNRy27jnKFOBJ2s7pfnFDzQ";
        final OAuth2Authentication auth = jwtTokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test
    public void whenTokenContainValidIssuer_thenSuccess() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg2NzkyOTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDZhMGVlZS1jNWE3LTQzNjEtYmM3NC02YzdlMDJkYWQzOWEiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.arlt3b9lMKarlc39TBHofp4uCbmvWmAqaGh28aeX3RU2qtzlYplHXZek2zx3EA2ZBy08ld-Y0heuWZoWfLA8aiwEWhgCYI4ratMMe8h4_PJPJnQ8CulvGK5M6gvDL0gqu4xoMh9VM0SseNvWkG40x4dcfsLbJt9IWhQqIkO4_8f4Ozv8iYvhFb-I4UNWe_LWKB8i7VC1y6mPDKjK0oBrVGH43P6ZtNClsCU4TLcAfVB9UMqQNbEf04gKEMmD2r94Pk6nEXg2JZ95B62SmwaqMEZMGegzThIF35Ii5Y2Svo0chFExRuLsrW__Q5nClwXJNRy27jnKFOBJ2s7pfnFDzQ";
        final OAuth2Authentication auth = jwtTokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenContainInvalidIssuer_thenException() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg2NzkyOTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDZhMGVlZS1jNWE3LTQzNjEtYmM3NC02YzdlMDJkYWQzOWEiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.arlt3b9lMKarlc39TBHofp4uCbmvWmAqaGh28aeX3RU2qtzlYplHXZek2zx3EA2ZBy08ld-Y0heuWZoWfLA8aiwEWhgCYI4ratMMe8h4_PJPJnQ8CulvGK5M6gvDL0gqu4xoMh9VM0SseNvWkG40x4dcfsLbJt9IWhQqIkO4_8f4Ozv8iYvhFb-I4UNWe_LWKB8i7VC1y6mPDKjK0oBrVGH43P6ZtNClsCU4TLcAfVB9UMqQNbEf04gKEMmD2r94Pk6nEXg2JZ95B62SmwaqMEZMGegzThIF35Ii5Y2Svo0chFExRuLsrW__Q5nClwXJNRy27jnKFOBJ2s7pfnFDzQ";
        final OAuth2Authentication auth = jwtTokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenDontContainUsername_thenException() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg2NzkyOTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDZhMGVlZS1jNWE3LTQzNjEtYmM3NC02YzdlMDJkYWQzOWEiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.arlt3b9lMKarlc39TBHofp4uCbmvWmAqaGh28aeX3RU2qtzlYplHXZek2zx3EA2ZBy08ld-Y0heuWZoWfLA8aiwEWhgCYI4ratMMe8h4_PJPJnQ8CulvGK5M6gvDL0gqu4xoMh9VM0SseNvWkG40x4dcfsLbJt9IWhQqIkO4_8f4Ozv8iYvhFb-I4UNWe_LWKB8i7VC1y6mPDKjK0oBrVGH43P6ZtNClsCU4TLcAfVB9UMqQNbEf04gKEMmD2r94Pk6nEXg2JZ95B62SmwaqMEZMGegzThIF35Ii5Y2Svo0chFExRuLsrW__Q5nClwXJNRy27jnKFOBJ2s7pfnFDzQ";
        final OAuth2Authentication auth = jwtTokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    public void whenTokenContainEmptyUsername_thenException() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg2NzkyOTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiI1MDZhMGVlZS1jNWE3LTQzNjEtYmM3NC02YzdlMDJkYWQzOWEiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il19.arlt3b9lMKarlc39TBHofp4uCbmvWmAqaGh28aeX3RU2qtzlYplHXZek2zx3EA2ZBy08ld-Y0heuWZoWfLA8aiwEWhgCYI4ratMMe8h4_PJPJnQ8CulvGK5M6gvDL0gqu4xoMh9VM0SseNvWkG40x4dcfsLbJt9IWhQqIkO4_8f4Ozv8iYvhFb-I4UNWe_LWKB8i7VC1y6mPDKjK0oBrVGH43P6ZtNClsCU4TLcAfVB9UMqQNbEf04gKEMmD2r94Pk6nEXg2JZ95B62SmwaqMEZMGegzThIF35Ii5Y2Svo0chFExRuLsrW__Q5nClwXJNRy27jnKFOBJ2s7pfnFDzQ";
        final OAuth2Authentication auth = jwtTokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }
}