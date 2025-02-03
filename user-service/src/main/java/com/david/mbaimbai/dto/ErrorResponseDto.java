package com.david.mbaimbai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Error Response", description = "Response for cases where processing ends with an exception.")
public class ErrorResponseDto {

    @Schema(description = "API path invoked by client", name = "Api Path")
    private String apiPath;

    @Schema(description = "Error code representing the error happened", name = "Status Code")
    private String statusCode;

    @Schema(description = "Error message representing the error happened", name = "Error Message")
    private String statusErrorMessage;

    @Schema(description = "Time of the error", name = "Time Of Error")
    private LocalDateTime errorTime;
}
