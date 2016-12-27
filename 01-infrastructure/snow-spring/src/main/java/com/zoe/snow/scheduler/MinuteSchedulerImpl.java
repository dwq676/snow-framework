package com.zoe.snow.scheduler;

import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/*
 * @author dwq
 */
@Component("snow.scheduler.minute")
public class MinuteSchedulerImpl extends SchedulerSupport implements Scheduler {
    @Autowired(required = false)
    protected Set<MinuteJob> minuteJobs;

    //@Scheduled(cron = "${commons.scheduler.minute.cron:* * * * ?}")
    @Override
    public void execute() {
        if (!Validator.isEmpty(minuteJobs))
            execute(minuteJobs.parallelStream().collect(Collectors.toList()));
    }
}
