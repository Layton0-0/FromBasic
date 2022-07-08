package triple.mile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "i_review", columnList = "createDate", unique = true))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity{
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID reviewId;
    private String reviewContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    @JsonBackReference
    private Place place;

    @OneToMany(mappedBy = "review")
    @JsonManagedReference
    private List<Photo> attachedPhotos = new ArrayList<>();

    public Review changeReview(String reviewContent){
        this.reviewContent = reviewContent;
        return this;
    }

    public void addPhotos(Photo photo) {
        this.attachedPhotos.add(photo);
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
