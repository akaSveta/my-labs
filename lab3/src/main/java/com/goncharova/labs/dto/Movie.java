package com.goncharova.labs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Main movies dto")
public class Movie {
    @Schema(description = "Movies ID", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")
    private UUID id;
    @Schema(description = "Movies title", requiredMode = Schema.RequiredMode.REQUIRED, pattern = "^[\\s\\S]{1,100}$")
    private String title;
    @Schema(description = "Movies rating", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String message;
}

