package triple.mile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import triple.mile.service.PhotoService;
import triple.mile.service.PlaceService;
import triple.mile.service.ReviewService;
import triple.mile.service.UserService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
//@Rollback(false)
@AutoConfigureMockMvc
class ReviewControllerTest {
    @Autowired
    UserService userService;
    @Autowired
    PlaceService placeService;
    @Autowired
    ReviewService reviewService;

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("response 단위 테스트")
    public void controllerResponseTest() throws Exception{
        Map<String, Object> input = new HashMap<>();
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어222");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    @DisplayName("리뷰 ADD 테스트")
    public void reviewAddTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", UUID.randomUUID().toString());
        input.put("content", "좋아여어어3468652");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("리뷰 MOD 테스트")
    public void reviewModTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));

        Review review = new Review(UUID.randomUUID(), "이거남아있지마라", user, place);
        reviewService.saveReview(review);
        place.addReviews(review);

        UUID reviewId = place.getReviews().get(0).getReviewId();
        String[] strings = {UUID.randomUUID().toString(),UUID.randomUUID().toString()};

        input.put("type", "REVIEW");
        input.put("action", "MOD");
        input.put("reviewId", reviewId.toString());
        input.put("content", "좋아여어어546456");
        input.put("attachedPhotoIds", strings);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("리뷰 DELETE 단위 테스트")
    public void reviewDeleteTest() throws Exception {
        Map<String, Object> input = new HashMap<>();
        User user = userService.saveUser(new User(UUID.randomUUID()));
        Place place = placeService.savePlace(new Place(UUID.randomUUID()));

        Review review = new Review(UUID.randomUUID(), "이거남아있지마라", user, place);
        reviewService.saveReview(review);
        place.addReviews(review);

        UUID reviewId = place.getReviews().get(0).getReviewId();

        input.put("type", "REVIEW");
        input.put("action", "DELETE");
        input.put("reviewId", reviewId.toString());
        input.put("content", null);
        input.put("attachedPhotoIds", null);
        input.put("userId", user.getUserId().toString());
        input.put("placeId", place.getPlaceId().toString());

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("포인트 조회 테스트")
    public void pointReadTest() throws Exception{
        Map<String, Object> input = new HashMap<>();
        List<User> users = userService.findAllUser();
        User user = users.get(0);

        input.put("userId", user.getUserId().toString());

        mockMvc.perform(post("/point").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());

    }

}