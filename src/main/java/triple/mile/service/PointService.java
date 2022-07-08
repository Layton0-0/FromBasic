package triple.mile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import triple.mile.entity.PointHistory;
import triple.mile.repository.PointRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PointService {
    private PointRepository pointRepository;

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointHistory savePointHistory(PointHistory pointHistory) {
        return pointRepository.save(pointHistory);
    }

    public List<PointHistory> findSameEvent(UUID sectionId, String pointType){
        return pointRepository.findBySectionIdAndPointType(sectionId, pointType);
    }


}
