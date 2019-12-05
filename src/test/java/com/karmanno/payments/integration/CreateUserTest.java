package com.karmanno.payments.integration;

import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.CreateUserRequest;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateUserTest extends IntegrationTest {

    @DisplayName("Should create user successfully")
    @Test
    public void shouldCreateUser() throws Exception {
        // given:
        CreateUserRequest request = createRequest();

        // when:
        HttpResponse httpResponse = targetPost("user", request);
        String jsonData = readJsonData(httpResponse);

        // then:
        Assertions.assertEquals(201, httpResponse.getStatusLine().getStatusCode());
        User user = objectMapper.readValue(jsonData, User.class);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getUsername());
    }

    @DisplayName("Should receive error when user exists")
    @Test
    public void userExists() throws Exception {
        // given:
        CreateUserRequest request = createRequest();
        EntityUtils.consume(targetPost("user", request).getEntity());

        // when:
        HttpResponse response = targetPost("user", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when username is empty")
    @Test
    public void usernameIsEmpty() {
        // given:
        CreateUserRequest request = createRequestWithEmptyUsername();

        // when:
        HttpResponse httpResponse = targetPost("user", request);

        // then:
        Assertions.assertEquals(400, httpResponse.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when username is null")
    @Test
    public void usernameIsNull() throws Exception {
        // given:
        CreateUserRequest request = createRequestWithNullUsername();

        // when:
        HttpResponse httpResponse = targetPost("user", request);

        // then:
        Assertions.assertEquals(400, httpResponse.getStatusLine().getStatusCode());
    }

    private CreateUserRequest createRequest() {
        return new CreateUserRequest()
                .setUsername("username");
    }

    private CreateUserRequest createRequestWithEmptyUsername() {
        return new CreateUserRequest()
                .setUsername("");
    }

    private CreateUserRequest createRequestWithNullUsername() {
        return new CreateUserRequest();
    }
}
