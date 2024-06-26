package com.keysolutions.nacionalservice.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketRequest {
    @JsonProperty("requestFieldValues")
    private RequestFieldValues requestFieldValues;

    @JsonProperty("requestTypeId")
    private String requestTypeId;

    @JsonProperty("serviceDeskId")
    private String serviceDeskId;
}
