package com.login.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class userDtoTest {

    private userDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new userDto();
        userDto.setUserId(1);
        userDto.setUsername("John Doe");
        userDto.setAccessToken("mock-token");
        userDto.setRole("USER");
    }

    @Test
    void testUserDtoGetters() {
        assertEquals(1, userDto.getUserId());
        assertEquals("John Doe", userDto.getUsername());
        assertEquals("mock-token", userDto.getAccessToken());
        assertEquals("USER", userDto.getRole());
    }

    @Test
    void testUserDtoSetters() {
        userDto.setUserId(2);
        userDto.setUsername("Jane Doe");
        userDto.setAccessToken("new-token");
        userDto.setRole("ADMIN");

        assertEquals(2, userDto.getUserId());
        assertEquals("Jane Doe", userDto.getUsername());
        assertEquals("new-token", userDto.getAccessToken());
        assertEquals("ADMIN", userDto.getRole());
    }

    @Test
    void testUserDtoEquals() {
        userDto userDto2 = new userDto();
        userDto2.setUserId(1);
        userDto2.setUsername("John Doe");
        userDto2.setAccessToken("mock-token");
        userDto2.setRole("USER");

        assertEquals(userDto, userDto2);
    }

    @Test
    void testUserDtoToString() {
        String expectedString = 
            "userDto(userId=1, username=John Doe, accessToken=mock-token, role=USER)";
        assertEquals(expectedString, userDto.toString());
    }

    @Test
    void testEmptyUserDto() {
        userDto emptyUserDto = new userDto();
        assertNotNull(emptyUserDto);
        assertEquals(0, emptyUserDto.getUserId());
        assertNull(emptyUserDto.getUsername());
        assertNull(emptyUserDto.getAccessToken());
        assertNull(emptyUserDto.getRole());
    }
}
