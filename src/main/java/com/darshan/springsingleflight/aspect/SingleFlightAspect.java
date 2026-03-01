package com.darshan.springsingleflight.aspect;


import com.darshan.springsingleflight.annotation.SingleFlight;
import com.darshan.springsingleflight.core.InFlightRegistry;
import com.darshan.springsingleflight.core.InflightEntry;
import com.darshan.springsingleflight.core.KeyResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
public class SingleFlightAspect {

    private final InFlightRegistry registry;
    private final KeyResolver keyResolver;
    private final Executor executor;

    public SingleFlightAspect(InFlightRegistry registry,    @Qualifier("singleFlightExecutor")  Executor executor) {
        this.registry = registry;
        this.executor = executor;
        this.keyResolver = new KeyResolver();
    }

    @Around("@annotation(singleFlight)")
    public Object around(ProceedingJoinPoint joinPoint, SingleFlight singleFlight) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();

        String resolvedKey = keyResolver.resolveKey(singleFlight.key(), method, joinPoint.getArgs());

        String key = method.getDeclaringClass().getName() + "#" + method.getName() + ":" + resolvedKey;

        InflightEntry existing = registry.get(key);

        if(existing != null)
        {
                return adaptReturnType(existing.getFuture(), returnType, singleFlight);
        }

        InflightEntry future = new InflightEntry();
        InflightEntry prev = registry.putIfAbsent(key, future);

        if(prev != null)
        {
            return adaptReturnType(prev.getFuture(), returnType, singleFlight);
        }

        executor.execute( () -> {
            try{
                Object result = joinPoint.proceed();
                future.getFuture().complete(result);
            } catch (Throwable e)
            {
                future.getFuture().completeExceptionally(e);
            }
        });

        return adaptReturnType(future.getFuture(), returnType, singleFlight);
    }

    private Object adaptReturnType(CompletableFuture<Object> future, Class<?> returnType, SingleFlight singleFlight) throws Throwable{
        if(CompletableFuture.class.isAssignableFrom(returnType))
        {
            return future.orTimeout(singleFlight.timeoutMs(), TimeUnit.MILLISECONDS);
        }

        try{
            return future.get(singleFlight.timeoutMs(), TimeUnit.MILLISECONDS);
        } catch(ExecutionException e)
        {
            throw e.getCause();
        }
    }

}
