package triple.mile.service;

import org.springframework.stereotype.Service;
import triple.mile.entity.Place;
import triple.mile.repository.PlaceRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    public Optional<Place> findByPlaceId(UUID placeId){ return placeRepository.findById(placeId);}
}
