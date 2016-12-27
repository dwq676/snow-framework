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
@Component("snow.scheduler.five-second")
public class FiveSecondSchedulerImpl extends SchedulerSupport implements Scheduler {
    @Autowired(required = false)
    protected Set<FiveSecondJob> fiveSecondJobs;

    @Scheduled(cron = "${commons.scheduler.five-seconds.cron:0/5 * *  * * ?}")
    @Override
    public void execute() {
        if (!Validator.isEmpty(fiveSecondJobs))
            execute(fiveSecondJobs.parallelStream().collect(Collectors.toList()));
    }
}
