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
@Table(name = "user_table", indexes = @Index(name = "i_user", columnList = "createDate", unique = true))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    // 사용자 포인트 총점
    private int userPoint;

    // 사용자 포인트 증감 이력
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<PointHistory> pointHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public User(UUID userId) {
        this.userId = userId;
        this.userPoint = 0;
    }

    public void addReviews(Review review) {
        this.reviews.add(review);
    }

    // 사용자 포인트 증가/감소
    public void changeUserPoint(int point, PointHistory pointHistory) {
        this.userPoint += point;
        this.pointHistory.add(pointHistory);
    }

}
