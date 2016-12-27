package com.zoe.snow.scheduler;

/**
 * 作业，实现了此作业的接口
 * 作业调度器会定时去执行此接口
 *
 * @author Dai Wenqing
 * @date 2016/4/21
 */
public interface Job {
    /*
     * 按照定时器执行作业
     */
    void running();

    /*获取作业名称*/
    default String getJobName() {
        return "";
    }
}
