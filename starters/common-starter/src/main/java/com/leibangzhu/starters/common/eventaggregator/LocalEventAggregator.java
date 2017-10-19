package com.leibangzhu.starters.common.eventaggregator;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalEventAggregator implements IEventAggregator {

    private static final Logger logger = LoggerFactory.getLogger(LocalEventAggregator.class);

    private Map<String, List<IEventHandler>> handlerMap = new HashMap<>();
    private ExecutorService threadPool;
    private int threadCount = 10;//默认线程数10

    public LocalEventAggregator(int threadCount) {
        this.threadCount = threadCount;
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public LocalEventAggregator() {
        this.threadPool = Executors.newFixedThreadPool(threadCount);
    }

    public synchronized void subscribe(IEvent event, IEventHandler handler) {
        List<IEventHandler> eventHandlers = handlerMap.computeIfAbsent(event.getName(), k -> new ArrayList<>());
        eventHandlers.add(handler);
    }

    public void publish(IEvent event) throws Exception {

        if (null == event) {
            return;
        }

        processHandlers(event);
    }

    protected void processHandlers(IEvent event) throws ForceRetryException {

        List<IEventHandler> eventHandlers = handlerMap.get(event.getName());
        if (CollectionUtils.isNotEmpty(eventHandlers)) {
            for (IEventHandler handler : eventHandlers) {


                if (handler.accept(event)) {

                    logger.info(String.format("Accept event '%s'", event.getName()));

                    try {

                        if (1 != threadCount) {
                            threadPool.execute(new LocalEventTask(event.getName(), handler, event.getParams()));
                        } else {
                            handler.handle(event.getParams());
                        }

                    } catch (ForceRetryException fe) {

                        throw fe;
                    } catch (Exception e) {

                        logger.error(String.format("Error while processing event '%s'", event.getName()), e);
                    }
                }
            }
        }
    }

    private class LocalEventTask implements Runnable {

        private IEventHandler localEventHandler;
        private Map<String, Object> params;
        private String eventName;

        private LocalEventTask(String eventName, IEventHandler localEventHandler, Map<String, Object> params) {
            this.localEventHandler = localEventHandler;
            this.params = params;
        }

        @Override
        public void run() {

            try {

                localEventHandler.handle(params);
            } catch (Exception e) {

                logger.error(String.format("Error while processing event '%s' in event task.", eventName), e);
            }
        }
    }
}
