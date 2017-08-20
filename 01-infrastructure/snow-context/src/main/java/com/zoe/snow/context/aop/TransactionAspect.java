package com.zoe.snow.context.aop;

import com.zoe.snow.crud.Result;
import com.zoe.snow.dao.Transaction;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.TypeConverter;
import com.zoe.snow.util.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import com.zoe.snow.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * TransactionAspect
 *
 * @author Dai Wenqing
 * @date 2016/2/26
 */
@Aspect
@Service
public class TransactionAspect {
    @Autowired(required = false)
    private Set<Transaction> transactionSet;
    private ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();
    private ThreadLocal<String> outerTransaction = new ThreadLocal<>();

    /*@After("anyMethod()")
    public void doAfter(JoinPoint jp) {

    }*/

    @Around("anyMethod()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        Object r = null;
        try {
            r = proceedingJoinPoint.proceed(args);
            if (r instanceof Result) {
                Result result = Result.class.cast(r);
                //执行事务，且返回执行成功，才提交事务
                //confirm(result.getData(), proceedingJoinPoint.getTarget().getClass().getName());
                commit(proceedingJoinPoint.getTarget().getClass().getName(), !result.isSuccess());
            } else {
                commit(proceedingJoinPoint.getTarget().getClass().getName(), r == null);
            }
        } catch (Throwable e) {
            if (transactionSet != null) {
                transactionSet.forEach(Transaction::rollback);
            }
            Logger.error(e, "执行事务出现了错误！");
        } finally {
            outerTransaction.remove();
            threadLocal.remove();
            return AopUtil.getResult(proceedingJoinPoint, r);
        }
    }


    /*private void confirm(boolean data, String simpleName) {
        //ProceedingJoinPoint proceedingJoinPoint = ; Result data = ;
        if (data instanceof Boolean) {
            if (Boolean.class.cast(data)) {
                commit(simpleName, false);
            } else
                commit(simpleName, true);
        } else if (data != null) {
            commit(simpleName, false);
        }
    }*/

    private void commit(String simpleName, boolean rollback) {
        if (!Validator.isEmpty(outerTransaction.get()))
            if (outerTransaction.get().equals(simpleName))
                if (transactionSet != null) {
                    if (rollback)
                        transactionSet.forEach(Transaction::rollback);
                    else
                        transactionSet.forEach(Transaction::commit);
                }
    }


    // @Pointcut("execution(* *..*Ctrl.*(..))")
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void anyMethod() {
    }

    @Before("anyMethod()")
    public void doBefore(JoinPoint jp) {
        if (!Validator.isEmpty(threadLocal.get()))
            return;
        if (transactionSet != null)
            transactionSet.forEach(Transaction::beginTransaction);
        threadLocal.set(true);
        outerTransaction.set(jp.getTarget().getClass().getName());
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        if (transactionSet != null)
            transactionSet.forEach(Transaction::rollback);
        Logger.error(ex, "TransactionAspect->" + "method " + jp.getTarget().getClass().getName() + "."
                + jp.getSignature().getName() + " throw exception，并时行了事务回滚");

        outerTransaction.remove();
        threadLocal.remove();
    }
}
