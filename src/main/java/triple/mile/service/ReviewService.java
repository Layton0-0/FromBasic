package triple.mile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triple.mile.entity.Review;
import triple.mile.repository.ReviewRepository;

import java.util.Optional;
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

    public Optional<Review> findByReviewId(UUID reviewId){
        return reviewRepository.findById(reviewId);
    }

    public void deleteReview(UUID reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
