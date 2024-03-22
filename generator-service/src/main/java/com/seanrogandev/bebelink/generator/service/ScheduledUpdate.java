package com.seanrogandev.bebelink.generator.service;

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

    /**
     * Periodically updates expired URLs by executing a database stored procedure.
     * The method is scheduled to run at a fixed rate of 12 hours (43200000 milliseconds).
     *
     * This scheduled task calls a stored procedure 'execute_update_active' within the database
     * that is expected to update rows without returning any result set. It logs the number of rows
     * updated or an error message if the execution fails.
     */
    @Scheduled(fixedRate = 43200000)
    public void updateExpiredUrls() {
        template.getDatabaseClient()
                .sql("CALL execute_update_active()")
                .then()
                .subscribe();
    }
}
