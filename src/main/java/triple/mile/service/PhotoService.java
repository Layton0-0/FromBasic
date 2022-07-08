package triple.mile.service;

import org.springframework.stereotype.Service;
import triple.mile.entity.Photo;
import triple.mile.entity.Review;
import triple.mile.repository.PhotoRepository;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public void deleteAllPhoto(Review review) {
        photoRepository.deleteAllByReview(review);
    }

    public List<Photo> allPhotos() {
        return photoRepository.findAll();
    }
}
