package com.sparta.company.exception;

public enum ErrorCode {

  NO_ERROR("0000", "OK"),

  NO_AUTH_ERROR("401", "권한이 없습니다."),

  ERROR_TOKEN_EXPIRED("9200", "만료된 토큰입니다. 다시 로그인해 주세요."),

  BAD_REQUEST("9400", "잘못된 요청입니다."),
  ERROR_ACCESS_DENIED("9403", "실행 권한이 없습니다."),
  NO_AUTH_PERMISSION_DENIED("9403", "사용 권한이 없습니다."),

  ERROR_SERVER_ERROR("9500", "서버 오류가 발생하였습니다."),
  ERROR_CALL_API("9801", "API 호출오류."),

  ERROR_INVALID_ARGUMENT("9601", "잘못된 인자가 전달되었습니다."),  // IllegalArgumentException에 대한 에러 코드
  ERROR_INVALID_STATE("9602", "객체의 상태가 유효하지 않습니다."), // IllegalStateException에 대한 에러 코드
  ERROR_EMPTY_DATA("9603", "데이터가 존재하지 않습니다"),
  ERROR_ALREADY_DELETED("9604", "데이터가 이미 삭제되었습니다"),

  ERROR_PARAMETERS("9901", "파라미터 오류."),
  ERROR_NOT_SUPPORTED_METHOD("9902", "지원하지 않는 Method 입니다."),
  ERROR_INTERNAL_API_PARAMETERS("9903", "내부 API 파라미터 오류."),
  ERROR_TIMEOUT("9904", "시간 초과"),
  ERROR_DEFAULT("9999", "오류가 발생하였습니다.");

  private String code;
  private String message;

  private ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public static boolean isOk(String code) {
    return ErrorCode.NO_ERROR.code.equals(code);
  }
}
