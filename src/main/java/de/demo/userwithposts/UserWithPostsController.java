package de.demo.userwithposts;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
public class UserWithPostsController {
    private final UserWithPostsService userWithPostsService;

    @RequestMapping(value = "/userwithposts/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<Map<String, Object>> getUserWithPosts(@PathVariable("userId") final Long userId) {

        final StopWatch stopWatch = new StopWatch("GET user with posts, userId=" + userId.toString());
        stopWatch.start();

        final DeferredResult<Map<String, Object>> result = new DeferredResult<>();

        userWithPostsService.getUserWithPosts(userId).handle((userWithPosts, ex) -> {
            stopWatch.stop();
            log.info("{}, {} seconds", stopWatch.getId(), stopWatch.getTotalTimeSeconds());
            return result.setResult(userWithPosts);
        });

        return result;
    }

}
