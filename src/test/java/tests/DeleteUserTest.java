package tests;

import com.github.javafaker.Faker;
import dto.ValidUserRequest;
import org.junit.jupiter.api.Test;

public class DeleteUserTest extends ApiBase {


    String endpoint = "/users/";
    Faker faker = new Faker();

    @Test
    public void successDeleteUser(){
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        String emailUser = postRequest(endpoint, 201, requestBody).body().jsonPath().getString("email");
        deleteRequest(endpoint+emailUser, 200);
    }
    @Test
    public void unsuccessfulDeleteUserAfterDelete(){
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        String createdEmail = postRequest(endpoint, 201, requestBody).body().jsonPath().getString("email");
        String emailUser = endpoint+createdEmail;
        deleteRequest(emailUser, 200);
        deleteRequest(emailUser, 404);
    }
    @Test
    public void unsuccessfulDeleteUserWithWrongMethodGet(){
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        String emailUser = postRequest(endpoint, 201, requestBody).body().jsonPath().getString("email");
        deleteRequestWithWrongMethodGet(endpoint+emailUser, 400);
    }
    @Test
    public void unsuccessfulDeleteUserWithInvalidEmail(){
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        String createdEmail = postRequest(endpoint, 201, requestBody).body().jsonPath().getString("email");
        String userEmail = endpoint+"wrong"+createdEmail;
        deleteRequest(userEmail, 404);
    }


}
