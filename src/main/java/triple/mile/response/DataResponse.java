package triple.mile.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DataResponse {
    // http 상태 코드
    private int status;

    // 전달 메시지
    private String message;

    // 응답 데이터
    private Object data;

    public DataResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
