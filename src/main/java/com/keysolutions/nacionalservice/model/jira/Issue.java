package com.keysolutions.nacionalservice.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    @JsonProperty("_expands")
    private List<String> expands;

    @JsonProperty("issueId")
    private String issueId;

    @JsonProperty("issueKey")
    private String issueKey;

    @JsonProperty("requestTypeId")
    private String requestTypeId;

    @JsonProperty("serviceDeskId")
    private String serviceDeskId;

    @JsonProperty("createdDate")
    private CreatedDate createdDate;

    @JsonProperty("reporter")
    private Reporter reporter;

    @JsonProperty("requestFieldValues")
    private List<RequestFieldValue> requestFieldValues;

    @JsonProperty("currentStatus")
    private CurrentStatus currentStatus;

    @JsonProperty("_links")
    private Links links;

    @Data
    public static class CreatedDate {
        @JsonProperty("iso8601")
        private String iso8601;

        @JsonProperty("jira")
        private String jira;

        @JsonProperty("friendly")
        private String friendly;

        @JsonProperty("epochMillis")
        private long epochMillis;
    }

    @Data
    public static class Reporter {
        @JsonProperty("accountId")
        private String accountId;

        @JsonProperty("emailAddress")
        private String emailAddress;

        @JsonProperty("displayName")
        private String displayName;

        @JsonProperty("active")
        private boolean active;

        @JsonProperty("timeZone")
        private String timeZone;

        @JsonProperty("_links")
        private Links links;
    }

    @Data
    public static class RequestFieldValue {
        @JsonProperty("fieldId")
        private String fieldId;

        @JsonProperty("label")
        private String label;

        @JsonProperty("value")
        private Object value;

        @JsonProperty("renderedValue")
        private RenderedValue renderedValue;
    }

    @Data
    public static class RenderedValue {
        @JsonProperty("html")
        private String html;
    }

    @Data
    public static class CurrentStatus {
        @JsonProperty("status")
        private String status;

        @JsonProperty("statusCategory")
        private String statusCategory;

        @JsonProperty("statusDate")
        private CreatedDate statusDate;
    }

    @Data
    public static class Links {
        @JsonProperty("jiraRest")
        private String jiraRest;

        @JsonProperty("web")
        private String web;

        @JsonProperty("self")
        private String self;

        @JsonProperty("agent")
        private String agent;
    }
}
