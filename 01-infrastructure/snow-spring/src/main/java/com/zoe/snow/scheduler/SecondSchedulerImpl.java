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
@Component("snow.scheduler.second")
public class SecondSchedulerImpl extends SchedulerSupport implements Scheduler {
    @Autowired(required = false)
    protected Set<SecondJob> secondJobs;

    //@Scheduled(cron = "${commons.scheduler.seconds.cron:* * * * * ?}")
    @Override
    public void execute() {
        if (!Validator.isEmpty(secondJobs))
            execute(secondJobs.parallelStream().collect(Collectors.toList()));
    }
}
