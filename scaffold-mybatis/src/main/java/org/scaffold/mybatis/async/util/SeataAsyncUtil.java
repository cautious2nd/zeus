package org.scaffold.mybatis.async.util;

import io.seata.core.context.RootContext;
import org.apache.commons.lang.StringUtils;
import org.scaffold.mybatis.async.context.SeataAsyncCallInfo;
import org.scaffold.mybatis.async.context.SeataAysncCallContext;
import org.scaffold.mybatis.async.functional.AsyncNoRevFunction;
import org.scaffold.mybatis.async.functional.AsyncRevFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class SeataAsyncUtil {
    private static final Logger logger = LoggerFactory.getLogger(SeataAsyncUtil.class);
    private ExecutorService executor;

    public SeataAsyncUtil(@Value("${seata.async.thread-num:20}") int threadNum) {
        executor = Executors.newFixedThreadPool(threadNum);
    }

    public <T> SeataAsyncCallInfo<T> async(AsyncRevFunction<T> func) {
        String xid = RootContext.getXID();

        Future<T> future = executor.submit(() -> {
            if (StringUtils.isNotEmpty(xid)) {
                logger.debug("xid is : {}", xid);
                RootContext.bind(xid);
            }

            try {
                return func.apply();
            } finally {
                if (null != RootContext.getXID()) {
                    RootContext.unbind();
                }
            }
        });
        SeataAsyncCallInfo<T> callInfo = new SeataAsyncCallInfo<>(future, xid);
        SeataAysncCallContext.addAsyncInfo(callInfo);
        return callInfo;
    }

    public <T> SeataAsyncCallInfo<T> asyncByXid(AsyncRevFunction<T> func,
                                                String xid, CountDownLatch count) {
        Future<T> future = executor.submit(() -> {
            if (StringUtils.isNotEmpty(xid)) {
                logger.debug("xid is : {}", xid);
                RootContext.bind(xid);
            }

            try {
                T t = func.apply();
                count.countDown();
                return t;
            } finally {
                if (null != RootContext.getXID()) {
                    RootContext.unbind();
                }
            }
        });

        SeataAsyncCallInfo<T> callInfo = new SeataAsyncCallInfo<>(future, xid);
        SeataAysncCallContext.addAsyncInfo(callInfo);
        return callInfo;
    }

    public <T> SeataAsyncCallInfo asyncNotReturnVal(AsyncNoRevFunction<T> func) {
        String xid = RootContext.getXID();

        Future<Boolean> future = executor.submit(() -> {
            if (StringUtils.isNotEmpty(xid)) {
                logger.debug("xid is : {}", xid);
                RootContext.bind(xid);
            }
            try {
                func.apply();

                return true;
            } finally {
                if (null != RootContext.getXID()) {
                    RootContext.unbind();
                }
            }
        });
        while (true) {
            if (future.isDone()) {
                break;
            }
        }

        SeataAsyncCallInfo<Boolean> callInfo = new SeataAsyncCallInfo<>(future, xid);
        SeataAysncCallContext.addAsyncInfo(callInfo);
        return callInfo;
    }
}
