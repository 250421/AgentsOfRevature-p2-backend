package com.anonymousibex.Agents.of.Revature.util;

import com.anonymousibex.Agents.of.Revature.dto.UserDto;
import com.anonymousibex.Agents.of.Revature.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUtilsTest {

    // ------ isValidPassword tests ------

    @Test
    void isValidPassword_nullOrTooShortOrTooLong_returnsFalse() {
        assertThat(UserUtils.isValidPassword(null)).isFalse();
        assertThat(UserUtils.isValidPassword("Ab1!")).isFalse();   // too short
        // generate a 31‑char valid pattern to test upper bound
        String longPwd = "A".repeat(31) + "a1!";
        assertThat(UserUtils.isValidPassword(longPwd)).isFalse();
    }

    @Test
    void isValidPassword_missingCharacterClasses_returnsFalse() {
        // missing uppercase
        assertThat(UserUtils.isValidPassword("password1!")).isFalse();
        // missing lowercase
        assertThat(UserUtils.isValidPassword("PASSWORD1!")).isFalse();
        // missing digit
        assertThat(UserUtils.isValidPassword("Password!")).isFalse();
        // missing special
        assertThat(UserUtils.isValidPassword("Password1")).isFalse();
    }

    @Test
    void isValidPassword_allRequirementsMet_returnsTrue() {
        assertThat(UserUtils.isValidPassword("Abcdef1!")).isTrue();
        assertThat(UserUtils.isValidPassword("XyZ123#@")).isTrue();
    }

    // ------ toUserDto test ------

    @Test
    void toUserDto_convertsAllFields() {
        User user = new User();
        user.setId(123L);
        user.setUsername("jdoe");

        UserDto dto = UserUtils.toUserDto(user);

        assertThat(dto.getId()).isEqualTo(123L);
        assertThat(dto.getUsername()).isEqualTo("jdoe");
    }

    // ------ clearSessionCookie test ------

    @Test
    void clearSessionCookie_addsExpiredJSessionIdCookie() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);

        UserUtils.clearSessionCookie(response);

        verify(response).addCookie(captor.capture());
        Cookie cookie = captor.getValue();

        assertThat(cookie.getName()).isEqualTo("JSESSIONID");
        assertThat(cookie.getValue()).isNull();
        assertThat(cookie.getPath()).isEqualTo("/");
        assertThat(cookie.isHttpOnly()).isTrue();
        assertThat(cookie.getMaxAge()).isZero();
    }
}
