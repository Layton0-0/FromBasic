package triple.mile.service;

import org.springframework.stereotype.Service;
import triple.mile.entity.Photo;
import triple.mile.repository.PhotoRepository;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }
}
