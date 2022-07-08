package triple.mile.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import triple.mile.repository.*;
import triple.mile.service.ReviewService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(false)
class EntityTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    PointRepository pointRepository;

    @Test
    @DisplayName("엔티티 생성 테스트")
    public void testEntity(){
        User user = userRepository.save(new User(UUID.randomUUID()));

        Place place = placeRepository.save(new Place(UUID.randomUUID()));

        Review review = reviewRepository.save(new Review(UUID.randomUUID(), "새 리뷰", user, place));
        user.addReviews(review);
        place.addReviews(review);

        Photo photo = photoRepository.save(new Photo(UUID.randomUUID(), review));
        review.addPhotos(photo);

        PointHistory pointHistory = pointRepository.save(new PointHistory(1, "REVIEW", review.getReviewId(), "CREATE_REVIEW", user));
        user.changeUserPoint(1, pointHistory);

        List<Review> byUser = reviewRepository.findByUser(user);
        assertThat(byUser).contains(review);
        byUser = user.getReviews();
        assertThat(byUser).contains(review);

    }

    @Test
    @DisplayName("user와 place 생성")
    public void createUserAndPlace() {
        User user = userRepository.save(new User(UUID.randomUUID()));

        Place place = placeRepository.save(new Place(UUID.randomUUID()));

        assertThat(user).isNotNull();
        assertThat(place).isNotNull();
    }

    @Test
    @DisplayName("Review에 저장")
    public void storeReviewTest(){
        User user = new User(UUID.randomUUID());
        userRepository.save(user);

        Place place = new Place(UUID.randomUUID());
        placeRepository.save(place);

        Review review = new Review(UUID.randomUUID(), "완전 조음", user, place);

        Review review1 = reviewService.saveReview(review);
        Assertions.assertThat(review.getReviewId()).isEqualTo(review1.getReviewId());
    }
}