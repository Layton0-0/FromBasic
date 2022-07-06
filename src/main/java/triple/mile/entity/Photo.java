package triple.mile.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"photoId"})
public class Photo {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID photoId;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Photo(UUID photoId, Review review) {
        if(review != null) {
            this.review = review;
        }
        this.photoId = photoId;
    }
}
