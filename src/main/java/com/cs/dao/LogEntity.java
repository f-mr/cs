package com.cs.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {

    @Id
    private String id;
    @Builder.Default
    private long eventDuration = 0;
    private String host;
    private String type;
    @Builder.Default
    private boolean alert = false;

    public LogEntity updateLogInfo(String host, String type, long duration, boolean alert) {
        updateHostIfApplicable(host);
        updateTypeIfApplicable(type);
        this.eventDuration = duration;
        this.alert = alert;

        return this;
    }

    private void updateHostIfApplicable(String host) {
        if (isNotBlank(host)) {
            this.host = host;
        }
    }

    private void updateTypeIfApplicable(String type) {
        if (isNotBlank(type)) {
            this.host = type;
        }
    }
}
