package com.sparta.user.dto.user_dto;

/**
 * 사용자 생성시 요청할 dto
 *
 * @param username
 * @param email
 * @param encodedPassword
 */
public record UserCreateDto(String username, String email, String encodedPassword) {}
