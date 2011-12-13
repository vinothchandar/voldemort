package voldemort.server.niosocket;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import voldemort.annotations.jmx.JmxGetter;
import voldemort.annotations.jmx.JmxManaged;
import voldemort.utils.DaemonThreadFactory;

/**
 * Thread pool to manage the store worker threads to do async IO operations,
 * submitted by the selection managers
 * 
 */
@JmxManaged(description = "Voldemort Server async store-worker thread")
public class AsyncStoreThreadPool extends ThreadPoolExecutor {

    public AsyncStoreThreadPool(int coreThreads, int maxThreads, int maxQueuedRequests) {
        super(coreThreads,
              maxThreads,
              0,
              TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<Runnable>(maxQueuedRequests),
              new DaemonThreadFactory("voldemort-niosocket-ioworker"),
              new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @JmxGetter(name = "numberOfActiveThreads", description = "The number of active threads.")
    public int getNumberOfActiveThreads() {
        return this.getActiveCount();
    }

    @JmxGetter(name = "numberOfThreads", description = "The total number of threads, active and idle.")
    public int getNumberOfThreads() {
        return this.getPoolSize();
    }

    @JmxGetter(name = "queuedRequests", description = "Number of requests in the queue waiting to execute.")
    public int getQueuedRequests() {
        return this.getQueue().size();
    }

}
