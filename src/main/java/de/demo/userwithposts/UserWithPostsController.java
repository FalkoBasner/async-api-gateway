package de.demo.userwithposts;

import de.demo.platform.error.AppException;
import de.demo.platform.http.error.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;

import static de.demo.platform.error.AppException.extractAppException;

@AllArgsConstructor
@Slf4j
@RestController
public class UserWithPostsController {
    private final UserWithPostsService userWithPostsService;

    @RequestMapping(value = "/userwithposts/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResponseEntity<Map<String, Object>>> getUserWithPosts(@PathVariable("userId") final Long userId) {

        final StopWatch stopWatch = new StopWatch("GET user with posts, userId=" + userId.toString());
        stopWatch.start();

        final DeferredResult<ResponseEntity<Map<String, Object>>> result = new DeferredResult<>();

        userWithPostsService.getUserWithPosts(userId).handle((userWithPosts, ex) -> {
            stopWatch.stop();
            log.info("{}, {} seconds", stopWatch.getId(), stopWatch.getTotalTimeSeconds());

            // TODO Move that error handling to some controller advice
            if (ex != null) {
                Optional<AppException> appException = extractAppException(ex);
                if (appException.isPresent()) {
                    if (appException.get() instanceof NotFoundException) {
                        return result.setResult(ResponseEntity.notFound().build());
                    }
                }
                return result.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            }

            return result.setResult(ResponseEntity.ok().body(userWithPosts));
        });

        return result;
    }

}
