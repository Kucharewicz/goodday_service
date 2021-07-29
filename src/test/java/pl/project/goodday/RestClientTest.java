package pl.project.goodday;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.project.goodday.clients.RestClient;
import pl.project.goodday.dtos.GoldenThoughtDto;

@SpringBootTest
class RestClientTest {
    @Autowired
    private RestClient restClient;


    @Test
    @DisplayName("Should return true if Collection of golden thoughts contains at least 1 element after fetching data from API")
    public void getGoldenThoughts(){
        GoldenThoughtDto[] goldenThoughtDtos = restClient.fetchGoldenThoughts();
        Assertions.assertEquals(1, goldenThoughtDtos.length);
    }
}