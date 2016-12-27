package com.zoe.snow.scheduler;

import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 定时器支持类。
 *
 * @author daiwenqing
 */
public class SchedulerSupport {
    @Autowired(required = false)
    //protected Set<SchedulerJobListener> listeners;
    protected final Set<Integer> runningJobs = Collections.synchronizedSet(new HashSet<>());

    /**
     * 判断指定Job是否正在运行。
     *
     * @param job 要验证的JobJob对象。
     * @return 如果正在运行则返回true；否则返回false。
     */
    protected boolean isRunning(Job job) {
        return job != null && runningJobs.contains(job.hashCode());
    }

    /**
     * 将Job设置为正在运行。
     *
     * @param job 要设置的Job对象。
     */
    protected void begin(Job job) {
        if (job != null)
            runningJobs.add(job.hashCode());
    }

    /**
     * 设置Job对象运行结束。
     *
     * @param job 要设置的Job对象。
     */
    protected void finish(Job job) {
        if (job != null)
            runningJobs.remove(job.hashCode());
    }

    public synchronized void execute(Collection<Job> jobs) {
        if (Validator.isEmpty(jobs))
            return;

        for (final Job job : jobs) {
            if (isRunning(job))
                continue;

            new Thread(() -> {
                begin(job);

                try {
                    job.running();
                    Logger.info("成功执行[{}]定时器任务！", job.getJobName());
                } catch (Exception e) {
                    Logger.error(e, "执行定时器[{}]任务时发生异常！", job.getJobName());
                }

                finish(job);
            }).start();
        }
    }
}
