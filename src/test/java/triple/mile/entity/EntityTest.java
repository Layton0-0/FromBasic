package triple.mile.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import triple.mile.repository.PhotoRepository;
import triple.mile.repository.PlaceRepository;
import triple.mile.repository.ReviewRepository;
import triple.mile.repository.UserRepository;
import triple.mile.service.ReviewService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
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

    @Test
    public void testEntity(){
        User user = new User(UUID.randomUUID());
        userRepository.save(user);

        Place place = new Place(UUID.randomUUID());
        placeRepository.save(place);

        Review review = new Review(UUID.randomUUID(), "넘 조아영", user, place);
        reviewRepository.save(review);

        Photo photo = new Photo(UUID.randomUUID(), review);
        photoRepository.save(photo);

        List<Review> byUser = reviewRepository.findByUser(user);
        assertThat(review).isIn(byUser);
    }

    @Test
    @DisplayName("Review에 저장")
    public void storeReviewTest(){
        User user = new User(UUID.randomUUID());
        userRepository.save(user);

        Place place = new Place(UUID.randomUUID());
        placeRepository.save(place);

        Review review = new Review(UUID.randomUUID(), "장난아냐", user, place);

        Review review1 = reviewService.saveReview(review);
        Assertions.assertThat(review.getReviewId()).isEqualTo(review1.getReviewId());
    }
}