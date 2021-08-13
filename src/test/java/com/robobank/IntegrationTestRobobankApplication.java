package com.robobank;

import com.robobank.models.Record;
import com.robobank.repositories.RecordRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/* Integration testing */


@SpringBootTest(classes = RobobankApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IntegrationTestRobobankApplication {

    @LocalServerPort
    private int port;

    @Autowired
    private RecordRepository recordRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();


    @Test
    public void addRecord() {
        Record record = new Record(null, 9876, 4567, 50.0, 5.0,55.0, "Successful Integration Test case");
        HttpEntity<Record> entity = new HttpEntity<Record>(record, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/save"),
                HttpMethod.POST, entity, String.class);

        Record record1 = recordRepository.findByTransactionRef(9876);
        assertEquals(9876, record1.getTransactionRef().intValue());
    }
    
     @Test
    public void InternalServerErrorTest() throws Exception {

        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/transaction/test"),
                HttpMethod.GET, entity, String.class);

        assertTrue(response.getBody().contains("INTERNAL_SERVER_ERROR"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
