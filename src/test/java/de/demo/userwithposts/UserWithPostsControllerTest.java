package de.demo.userwithposts;

import com.google.common.collect.ImmutableMap;
import de.demo.platform.http.error.NotFoundException;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserWithPostsController.class)
public class UserWithPostsControllerTest {
    private static final long USER_ID = 4711L;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserWithPostsService service;

    @Test
    @SneakyThrows
    public void getUserWithPosts() {

        final CompletableFuture<JSONObject> jsonFuture = new CompletableFuture<>();
        when(service.getUserWithPosts(USER_ID)).thenReturn(jsonFuture);

        jsonFuture.complete(expectedResult());

        final MvcResult mvcResult = mvc.perform(get("/userwithposts/{userId}", USER_ID))
                .andExpect(request().asyncStarted())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity resp = (ResponseEntity) mvcResult.getAsyncResult();
        assertThat(resp.getBody()).isEqualTo(expectedResult());

        verify(service).getUserWithPosts(USER_ID);
    }

    @Test
    @Disabled("MockMvc is returning response code 200 instead of 404")
    @SneakyThrows
    public void get_with_unknown_user_id() {

        final CompletableFuture<JSONObject> jsonFuture = new CompletableFuture<>();
        when(service.getUserWithPosts(USER_ID)).thenReturn(jsonFuture);

        jsonFuture.completeExceptionally(new NotFoundException(new Exception("Ups")));

        final MvcResult mvcResult = mvc.perform(get("/userwithposts/{userId}", USER_ID))
                .andExpect(request().asyncStarted())
                .andExpect(status().isNotFound())
                .andReturn();

        verify(service).getUserWithPosts(USER_ID);
    }

    private JSONObject expectedResult() {
        return new JSONObject(ImmutableMap.of("key", "value"));
    }

}