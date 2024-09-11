package com.sparta.user.dto.user_dto;

/**
 * 사용자 정보가 필요할 때 전달받을 dto
 *
 * @param userId
 * @param username
 * @param email
 * @param password
 * @param role
 * @param isDelete
 */
public record UserDto(
    Long userId,
    String username,
    String email,
    String password,
    String role,
    Boolean isDelete
) {

}
