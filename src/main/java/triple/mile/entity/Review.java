package triple.mile.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"reviewId", "reviewComment"})
public class Review {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "review")
    private List<Photo> attachedPhotoIds = new ArrayList<>();

    public Review changeReview(String reviewContent){
        this.reviewContent = reviewContent;
        return this;
    }

    public Review(UUID reviewId, String reviewContent, User user, Place place) {
        this.reviewContent = reviewContent;
        this.reviewId = reviewId;
        if(user != null) {
            this.user = user;
        }
        if(place != null) {
            this.place = place;
        }
    }
}
