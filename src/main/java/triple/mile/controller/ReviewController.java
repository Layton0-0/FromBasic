package triple.mile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import triple.mile.entity.Place;
import triple.mile.entity.Review;
import triple.mile.entity.User;
import triple.mile.response.ActionEnum;
import triple.mile.response.DataResponse;
import triple.mile.response.StatusEnum;
import triple.mile.service.PhotoService;
import triple.mile.service.PlaceService;
import triple.mile.service.ReviewService;
import triple.mile.service.UserService;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final PlaceService placeService;
    private final PhotoService photoService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, PlaceService placeService, PhotoService photoService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.placeService = placeService;
        this.photoService = photoService;
    }

    @PostMapping("/events")
    @ResponseBody
    public ResponseEntity<DataResponse> pointEarn(@RequestBody Map<String, Object> body){
//        if((String)body.get("type") != TypeEnum.REVIEW.name()) {
//            return null;
//        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String action = (String) body.get("action");
        String content = (String) body.get("content");

        Object attachedPhotoIds = body.get("attachedPhotoIds");

        // user 찾기
        UUID userId = UUID.fromString((String)body.get("userId"));
        User user = userService.findByUserId(userId).get();
        if(user == null){
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // place 찾기
        UUID placeId = UUID.fromString((String)body.get("placeId"));
        Place place = placeService.findByPlaceId(placeId).get();
        if(place == null){
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        // action에 따라 review 찾거나 새로 생성, 각 action에 맞는 결과 수행
        UUID reviewId = UUID.fromString((String) body.get("reviewId"));
        Review review = null;
        if(action.equals(ActionEnum.ADD.name())){
            review = new Review(reviewId, (String)body.get("content"), user, place);
            reviewService.saveReview(review);
        } else if(action.equals(ActionEnum.MOD.name())){
            review = reviewService.findByReviewId(reviewId).get();
            review = review.changeReview(content);
        } else if(action.equals(ActionEnum.DELETE.name())) {
            review = reviewService.findByReviewId(reviewId).get();
            reviewService.deleteReview(reviewId);
        } else {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        DataResponse dataResponse = new DataResponse(StatusEnum.OK.getCode(), StatusEnum.OK.getMessage(), review);

        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }

    @PostMapping("/show")
    @ResponseBody
    public ResponseEntity<DataResponse> pointShow(){
        return null;
    }
}
