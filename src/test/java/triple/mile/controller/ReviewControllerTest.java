package triple.mile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import triple.mile.entity.Place;
import triple.mile.entity.Review;
import triple.mile.entity.User;
import triple.mile.service.ReviewService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Rollback(false)
@AutoConfigureMockMvc
class ReviewControllerTest {
    @Autowired
    ReviewService reviewService;

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Test
    @DisplayName("포인트 테스트 1")
    public void pointEarnTest() throws Exception{
        Map<String, String> input = new HashMap<>();

        input.put("type", "REVIEW");
        input.put("action", "ADD");
        input.put("reviewId", "240a0658-dc5f-4878-9381-ebb7b2667772");
        input.put("content", "좋아여어어");
        input.put("attachedPhotoIds", "[e4d1a64e-a531-46de-88d0-ff0ed70c0bb8, afb0cef2-851d-4a50-bb07-9cc15cbdc332]");
        input.put("userId", "3ede0ef2-92b7-4817-a5f3-0c575361f745");
        input.put("placeId", "2e4baf1c-5acb-4efb-a1af-eddada31b00f");

//        Mockito.when(reviewService.saveReview()).thenReturn(Review);

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk()).andDo(print());

    }



}