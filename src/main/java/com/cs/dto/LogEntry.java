package com.cs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.cs.common.LogState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogEntry {

    @JsonProperty
    private String id;

    @JsonProperty
    private LogState state;

    @JsonProperty
    private long timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private String host;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private String type;

}
