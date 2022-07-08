package triple.mile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "i_photo", columnList = "createDate", unique = true))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BaseTimeEntity{

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID photoId;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @JsonBackReference
    private Review review;

    public Photo(UUID photoId, Review review) {
        if(review != null) {
            this.review = review;
        }
        this.photoId = photoId;
    }
}
