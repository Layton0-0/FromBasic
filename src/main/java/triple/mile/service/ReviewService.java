package triple.mile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triple.mile.entity.Place;
import triple.mile.entity.Review;
import triple.mile.entity.User;
import triple.mile.repository.ReviewRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review findByReviewId(UUID reviewId){
        return reviewRepository.findByReviewId(reviewId);
    }

    public void deleteReview(UUID reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> findByPlaceAndUser(Place place, User user) {
        return reviewRepository.findByPlaceAndUser(place, user);
    }
}
