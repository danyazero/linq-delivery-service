package org.zero.npservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.zero.npservice.exception.RequestException;
import org.zero.npservice.model.UserData;

@Component
public class UserProvider {

    public UserData getUserData(String userId){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        System.out.println("UserId: "+userId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/user")
                .queryParam("userId", Base64Encoder.apply(userId));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                entity, UserData.class);

        if (response.getStatusCode() != HttpStatusCode.valueOf(200)) throw new RequestException("Error getting user data");
        return response.getBody();

//        return Deserializer.apply(response.body(), UserData.class);
    }
}
