package com.anonymousibex.Agents.of.Revature.util;

import com.anonymousibex.Agents.of.Revature.dto.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

class ExceptionResponseUtilsTest {

    @Test
    void buildResponse_setsMessageStatusAndTimestamp() {
        // given
        String msg = "Something went wrong";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // when
        ResponseEntity<ResponseDto> entity = ExceptionResponseUtils.buildResponse(msg, status);

        // then: outer ResponseEntity
        assertThat(entity.getStatusCode()).isEqualTo(status);

        // then: body fields
        ResponseDto body = entity.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo(msg);
        assertThat(body.getStatusCode()).isEqualTo(status.value());

        // timestamp should be recent (within 1 second)
        Instant now = Instant.now();
        assertThat(body.getTimestamp())
                .isNotNull()
                .isBeforeOrEqualTo(now)
                .isAfter(now.minus(Duration.ofSeconds(1)));
    }
}
