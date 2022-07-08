package triple.mile.response;

import lombok.Getter;

@Getter
public enum PointEnum {
    FIRST_USER_REVIEW(1, "첫 리뷰 보너스 점수"),
    CREATE_REVIEW(1, "리뷰 작성 점수"),
    PHOTO_REVIEW(1, "사진 첨부 점수"),
    DELETE_REVIEW(-1, "리뷰 삭제로 점수 회수"),
    DELETE_PHOTO(-1, "사진 삭제로 점수 회수"),
    DELETE_FIRST_POINT(-1, "리뷰 삭제로 보너스 점수 회수");

    private final int point;
    private final String message;

    PointEnum(int point, String message) {
        this.point = point;
        this.message = message;
    }
}
