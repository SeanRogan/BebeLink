package com.seanrogandev.bebelink.generator.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledUpdate {
    private final R2dbcEntityTemplate template;

    @Autowired
    ScheduledUpdate(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 43200000)
    public void updateExpiredUrls() {
        template.getDatabaseClient()
                .sql("CALL execute_update_active()")
                .fetch()
                .rowsUpdated()
                .subscribe(count -> System.out.println("Function executed, rows updated: " + count),
                        error -> System.err.println("Error executing function: " + error.getMessage()));
    }
}
