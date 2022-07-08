package triple.mile.response;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    // 1. type이 review가 아닐 경우
    WRONG_TYPE(400, "타입이 틀렸습니다", "E001"),

    // 2. userId에 해당하는 user 정보가 없는 경우
    NOT_USER(400, "존재하지 않는 사용자입니다", "E002"),

    // 3. placeId에 해당하는 place 정보가 없는 경우
    NOT_PLACE(400, "존재하지 않는 장소입니다", "E003"),

    // 4. 리뷰를 1개 이상 쓰려고 할 때
    MORE_THAN_ONE_REVIEW(400, "리뷰는 하나만 작성할 수 있습니다", "E004"),

    // 5. action 값이 add, mod, delete 외의 것일 경우
    WRONG_ACTION(400, "작성, 수정, 삭제 중에 선택해주세요", "E005");

    private int status;
    private String message;
    private String code;

    ErrorEnum(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
