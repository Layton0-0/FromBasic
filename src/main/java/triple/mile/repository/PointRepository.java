package triple.mile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mile.entity.PointHistory;

import java.util.List;
import java.util.UUID;

@Repository
public interface PointRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findBySectionIdAndPointType(UUID sectionId, String pointType);
}
