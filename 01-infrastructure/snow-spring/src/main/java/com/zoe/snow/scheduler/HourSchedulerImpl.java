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
@Component("snow.scheduler.hour")
public class HourSchedulerImpl extends SchedulerSupport implements Scheduler {
    @Autowired(required = false)
    protected Set<HourJob> hourJobs;

    //@Scheduled(cron = "${commons.scheduler.hour.cron:* * * ?}")
    @Override
    public void execute() {
        if (!Validator.isEmpty(hourJobs))
            execute(hourJobs.parallelStream().collect(Collectors.toList()));
    }
}
