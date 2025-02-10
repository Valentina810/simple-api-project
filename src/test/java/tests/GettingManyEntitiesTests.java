package tests;

import io.qameta.allure.AllureId;
import model.resource.Resource;
import model.response.GetResourceResponse;
import model.response.GetUsersResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.toSet;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(value = CONCURRENT)
public class GettingManyEntitiesTests extends TestBase {

    @Test
    @AllureId("67")
    @DisplayName("Получение списка пользователей")
    public void testGetUsers() {
        GetUsersResponse getUsersResponse = given().get("/api/users?page=2")
                .then().statusCode(SC_OK).log().body()
                .extract().body().as(GetUsersResponse.class);
        assertAll(
                () -> assertNotNull(getUsersResponse),
                () -> assertFalse(getUsersResponse.getData().isEmpty())
        );
    }

    @Test
    @AllureId("68")
    @DisplayName("Получение списка ресурсов(цвета)")
    public void testGetResources() {
        Set<String> expectedData = Set.of("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise");
        GetResourceResponse getResourceResponse = given().get("/api/unknown")
                .then().statusCode(SC_OK).log().body()
                .extract().body().as(GetResourceResponse.class);
        assertAll(
                () -> assertNotNull(getResourceResponse),
                () -> assertEquals(expectedData, getResourceResponse.getData().stream().map(Resource::getName).collect(toSet()))
        );
    }
}
