package org.sergei.manager;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergei Visotsky
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AircraftControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAircraftByModelNumberTest() {
        String request = "{\"modelNumber\": \"AS-421\"}";
        ResponseEntity<AircraftDTO> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/getAircraftByModelNumber", request, AircraftDTO.class);
        assertEquals(404, response.getStatusCodeValue());
    }

}
