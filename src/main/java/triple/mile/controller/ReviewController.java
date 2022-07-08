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
import triple.mile.entity.*;
import triple.mile.exception.*;
import triple.mile.response.DataResponse;
import triple.mile.response.StatusEnum;
import triple.mile.response.TypeEnum;
import triple.mile.service.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static triple.mile.response.ActionEnum.*;
import static triple.mile.response.PointEnum.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final PlaceService placeService;
    private final PhotoService photoService;
    private final PointService pointService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, PlaceService placeService, PhotoService photoService, PointService pointService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.placeService = placeService;
        this.photoService = photoService;
        this.pointService = pointService;
    }

    @PostMapping("/events")
    @ResponseBody
    public ResponseEntity<DataResponse> pointEarn(@RequestBody Map<String, Object> body){

        // 1. type이 review가 아닐 경우
        if(!String.valueOf(body.get("type")).equals(TypeEnum.REVIEW.name())) {
            throw new WrongTypeException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // user 찾기
        UUID userId = UUID.fromString((String)body.get("userId"));
        User user = userService.findByUserId(userId);
        // 2. userId에 해당하는 user 정보가 없는 경우
        if(user == null){
            throw new NotUserException();
        }

        // place 찾기
        UUID placeId = UUID.fromString((String)body.get("placeId"));
        Place place = placeService.findByPlaceId(placeId);
        // 3. placeId에 해당하는 place 정보가 없는 경우
        if(place == null){
            throw new NotPlaceException();
        }

        String action = (String) body.get("action");
        String reviewContent = (String) body.get("content");
        String type = (String) body.get("type");

        // action에 따라 review 찾거나 새로 생성, 각 action에 맞는 결과 수행
        UUID reviewId = UUID.fromString((String) body.get("reviewId"));
        List<String> attachedPhotoIds = (ArrayList) body.get("attachedPhotoIds");
        Review review = null;

        // 변화될 포인트
        int point = 0;

        // ADD(리뷰 추가 -> 글+1, 사진여부+1, 첫리뷰여부+1 체크)
        if(action.equals(ADD.name())){
            // 4. 리뷰를 1개 이상 쓰려고 할 때
            if(!reviewService.findByPlaceAndUser(place, user).isEmpty()){
                throw new MoreThanOneReviewException();
            }
            // 글 +1
            point = CREATE_REVIEW.getPoint();
            user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, CREATE_REVIEW.name(), user)));
            // 사진 +1
            if(attachedPhotoIds != null) {
                point = PHOTO_REVIEW.getPoint();
                user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, PHOTO_REVIEW.name(), user)));
            }
            // 첫리뷰여부
            if(placeService.findByPlaceId(placeId).getReviews() == null) {
                point = FIRST_USER_REVIEW.getPoint();
                user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, FIRST_USER_REVIEW.name(), user)));
            }
            review = new Review(reviewId, reviewContent, user, place);
            reviewService.saveReview(review);
            user.addReviews(review);
            place.addReviews(review);
        }

        // MOD(리뷰 수정 -> 사진 추가+1/사진 전부 삭제-1)
        else if(action.equals(MOD.name())){
            review = reviewService.findByReviewId(reviewId);
            review = review.changeReview(reviewContent);
            // 기존에 사진 첨부를 안했으면
            if(review.getAttachedPhotos() == null) {
                // 첨부된 사진이 있으면(새로 사진 첨부)
                if(attachedPhotoIds != null) {
                    point = PHOTO_REVIEW.getPoint();
                    user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, PHOTO_REVIEW.name(), user)));
                }
            }
            // 기존에 사진 첨부를 했으면
            else {
                // 첨부된 사진이 없으면(기존 사진 전부 삭제)
                if(attachedPhotoIds == null) {
                    point = DELETE_PHOTO.getPoint();
                    user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, DELETE_PHOTO.name(), user)));
                }
            }
            reviewService.saveReview(review);
        }

        // DELETE(리뷰 삭제 -> 내용 점수[사진, 글], 보너스 점수 회수)
        else if(action.equals(DELETE.name())) {
            review = reviewService.findByReviewId(reviewId);
            // 사진 있었으면
            if(review.getAttachedPhotos() != null) {
                point = DELETE_PHOTO.getPoint();
                user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, DELETE_PHOTO.name(), user)));
            }

            if(pointService.findSameEvent(reviewId, FIRST_USER_REVIEW.getMessage()) != null) {
                point = DELETE_FIRST_POINT.getPoint();
                user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, DELETE_FIRST_POINT.name(), user)));
            }
            point = DELETE_REVIEW.getPoint();
            user.changeUserPoint(point, pointService.savePointHistory(new PointHistory(point, type, reviewId, DELETE_REVIEW.name(), user)));

            photoService.deleteAllPhoto(review);
            reviewService.deleteReview(reviewId);
        }
        // 5. action 값이 add, mod, delete 외의 것일 경우
        else {
            throw new WrongActionException();
        }

        // photoId 저장
        if(attachedPhotoIds != null) {
            for (String attachedPhotoId: attachedPhotoIds) {
                UUID photoId = UUID.fromString(attachedPhotoId);
                if(!photoService.allPhotos().isEmpty()) {
                    photoService.deleteAllPhoto(review);
                }
                Photo photo = photoService.savePhoto(new Photo(photoId, review));
                review.addPhotos(photo);
            }
        }
        else if(!action.equals(DELETE.name())) {
            photoService.deleteAllPhoto(review);
        }

        DataResponse dataResponse = new DataResponse(StatusEnum.OK.getCode(), StatusEnum.OK.getMessage(), review);

        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }

    @PostMapping("/point")
    @ResponseBody
    public ResponseEntity<DataResponse> pointRead(@RequestBody Map<String, String> body){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON));
        User user = userService.findByUserId(UUID.fromString(body.get("userId")));
        int totalPoint = user.getUserPoint();

        DataResponse dataResponse = new DataResponse(StatusEnum.OK.getCode(), StatusEnum.OK.getMessage(), totalPoint);
        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }
}
