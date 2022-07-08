package triple.mile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mile.entity.Photo;
import triple.mile.entity.Review;

import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    Photo findByPhotoId(UUID photoId);

    void deleteAllByReview(Review review);

}
