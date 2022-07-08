package triple.mile.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import triple.mile.entity.Place;
import triple.mile.entity.Review;
import triple.mile.entity.User;
import triple.mile.service.PlaceService;
import triple.mile.service.ReviewService;
import triple.mile.service.UserService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
//@Rollback(false)
@AutoConfigureMockMvc
public class ExceptionTest {
    @Autowired
    UserService userService;
    @Autowired
    PlaceService placeService;
    @Autowired
    ReviewService reviewService;
    
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    @DisplayName("리뷰 개수 초과 테스트")
    public void moreThanOneReviewExceptionTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));

        Review review = reviewService.saveReview(new Review(UUID.randomUUID(), "리뷰 개수 테스트", user, place));
        user.addReviews(review);
        place.addReviews(review);

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        Assertions.assertThrows(MoreThanOneReviewException.class, () -> {
            if(reviewService.findByPlaceAndUser(place, user) != null){
                throw new MoreThanOneReviewException();
            }
        });
    }

    @Test
    @DisplayName("유저 없음 테스트")
    public void notUserExceptionTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};
        UUID userId = UUID.randomUUID();
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", userId.toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        User user = userService.findByUserId(userId);

        Assertions.assertThrows(NotUserException.class, () -> {
            if(user == null){
                throw new NotUserException();
            }
        });
    }

    @Test
    @DisplayName("장소 없음 테스트")
    public void notPlaceExceptionTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};
        User user = userService.saveUser(new User(UUID.randomUUID()));
        UUID placeId = UUID.randomUUID();

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", placeId.toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        Place place = placeService.findByPlaceId(placeId);

        Assertions.assertThrows(NotPlaceException.class, () -> {
            if(place == null){
                throw new NotPlaceException();
            }
        });

    }

    @Test
    @DisplayName("액션 이상 테스트")
    public void wrongActionExceptionTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));
        String action = "WHAT";

        input.put("type", "REVIEW");
        input.put("action", action);
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        Assertions.assertThrows(WrongActionException.class, () -> {
            if(!(action.equals("ADD") || action.equals("MOD") || action.equals("DELETE"))) {
                throw new WrongActionException();
            }
        });

    }

    @Test
    @DisplayName("타입 이상 테스트")
    public void wrongTypeExceptionTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));
        String type = "PLAN";

        input.put("type", type);
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        Assertions.assertThrows(WrongTypeException.class, () -> {
            if(!type.equals("REVIEW")) {
                throw new WrongTypeException();
            }
        });

    }

}
