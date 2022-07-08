package triple.mile.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "i_place", columnList = "createDate", unique = true))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseTimeEntity{

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID placeId;

    @OneToMany(mappedBy = "place")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public Place(UUID placeId) {
        this.placeId = placeId;
    }

    public void addReviews(Review review) {
        this.reviews.add(review);
    }
}
