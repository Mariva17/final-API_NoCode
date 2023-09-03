package tests;

import com.github.javafaker.Faker;
import dto.ValidUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class CreateUserTest extends ApiBase {

    String endpoint = "/users/";
    Faker faker = new Faker();
    String email = faker.internet().emailAddress();

    @AfterEach
    public void deleteCreatedUser() {
        deleteRequestForInvalidUser(endpoint+email);
    }

    @Test
    public void successfulCreateUser() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();
        Response response = postRequest(endpoint, 201, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithoutHeaderApiKey() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequestWithInvalidSpec1(endpoint, 401, requestBody);
    }
    @Test
    public void unsuccessfulCreateUserWithoutHeaderDomain() {

        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequestWithInvalidSpec2(endpoint, 400, requestBody);

    }
    @Test
    public void unsuccessfulCreateUserWithShortFullName() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name("j")
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);
    }
    @Test
    public void unsuccessfulCreateUserWithSymbolsFullName() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name("§$%%/*-+")
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();
        Response response = postRequest(endpoint, 400, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithShortPassword() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password("1234")
                .generate_magic_link(false)
                .build();
        Response response = postRequest(endpoint, 400, requestBody);

    }

    @Test
    public void unsuccessfulCreateUserWithTooLongPassword() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password("12341233462222222222222222222222222222222222222222222222222222")
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);

    }

    @Test
    public void unsuccessfulCreateUserWithSymbolsPassword() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password("*//-+$%=")
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);

    }

    @Test
    public void unsuccessfulCreateUserWithInvalidEmail() {
        String emailUser = "johnRichgmail.com";
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);

    }

    @Test
    public void unsuccessfulCreateUserWithEmptyEmail() {

        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email("")
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithEmptyFullName() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name("")
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithEmptyPassword() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password("")
                .generate_magic_link(false)
                .build();

        Response response = postRequest(endpoint, 400, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithEmptyBody() {

        ValidUserRequest requestBody = ValidUserRequest.builder()
                .build();

        Response response = postRequest(endpoint, 400, requestBody);
    }

    @Test
    public void unsuccessfulCreateUserWithExistingEmail() {

        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(faker.internet().emailAddress())
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();
        Response response = postRequest(endpoint, 201, requestBody);
        String createdEmail = response.body().jsonPath().getString("email");
        ValidUserRequest requestBodyWithExistingEmail = ValidUserRequest.builder()
                .email(createdEmail)
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();
        Response response2 = postRequest(endpoint, 400, requestBodyWithExistingEmail);

    }

    @Test
    public void unsuccessfulCreateUserWithMethodGet() {
        String emailUser = email;
        ValidUserRequest requestBody = ValidUserRequest.builder()
                .email(emailUser)
                .full_name(faker.internet().uuid())
                .password(faker.internet().password())
                .generate_magic_link(false)
                .build();

        Response response = postRequestWithMethodGet(endpoint, 400, requestBody);
    }



}
