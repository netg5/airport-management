/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservation.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

/**
 * @author Sergei Visotsky
 */
@Aspect
@Slf4j
public class PerformanceAspect {

    private static final String LOG_MESSAGE_FORMAT = "%s.%s execution time: %d ms";

    /**
     * Pointcut that matches all Service annotations
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void springBeanPointCut() {
        // Implementation comes in advices.
    }

    /**
     * Pointcut that matches all service package
     */
    @Pointcut("within(org.sergei.reservation.service..*)")
    public void applicationPackagePointcut() {
        // Implementation comes in advices.
    }

    /**
     * Pointcut that measures method time execution
     *
     * @param joinPoint join point for advice
     * @return result time elapsed
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointCut() && applicationPackagePointcut()")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object retVal = joinPoint.proceed();
        stopWatch.stop();
        logExecutionTime(joinPoint, stopWatch);
        return retVal;
    }

    /**
     * Logging execution time - output into the console
     *
     * @param joinPoint join point for advice
     * @param stopWatch Time estimation class
     */
    private void logExecutionTime(ProceedingJoinPoint joinPoint, StopWatch stopWatch) {
        String logMessage = String.format(LOG_MESSAGE_FORMAT,
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        log.info(logMessage);
    }
}
