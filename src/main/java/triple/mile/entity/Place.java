package triple.mile.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"placeId"})
public class Place {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID placeId;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    public Place(UUID placeId) {
        this.placeId = placeId;
    }
}
