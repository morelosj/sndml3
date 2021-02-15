package sndml.datamart;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sndml.servicenow.Log;


public class Daemon implements org.apache.commons.daemon.Daemon {

	static Logger logger = LoggerFactory.getLogger(Daemon.class);
		
	private final ConnectionProfile profile;
	private final ExecutorService workerPool;
	private final String agentName;
	private final int intervalSeconds;
	private final int threadCount;
	private final Scanner scanner;
	private Timer timer;
	
	public Daemon(ConnectionProfile profile) {
		this.profile = profile;
		threadCount = profile.getPropertyInt("daemon.threads", 3);
		intervalSeconds = profile.getPropertyInt("daemon.interval_seconds", 20);
		agentName = profile.getProperty("loader.agent", "main");		
		assert threadCount > 0;
		assert intervalSeconds > 0;
		workerPool = Executors.newFixedThreadPool(threadCount);
        scanner = new Scanner(profile, workerPool);
	}
	
	public void run() throws Exception {
		Log.setGlobalContext();
		if (logger.isDebugEnabled()) logger.debug(Log.INIT, "Debug is enabled");
		start();
		while (!workerPool.isTerminated()) {
			Log.setJobContext(agentName);			
			logger.info(Log.DAEMON, "main awaiting threadpool termination");
			workerPool.awaitTermination(120, TimeUnit.SECONDS);
		}
		stop();
	}

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		logger.info(Log.INIT, "begin init");		
	}

	@Override
	public void start() throws Exception {
		logger.info(Log.INIT, String.format("interval=%d seconds", intervalSeconds));								
        this.timer = new Timer("scanner", true);
		ShutdownHook shutdownHook = new ShutdownHook(profile, scanner, workerPool);
		Runtime.getRuntime().addShutdownHook(shutdownHook);		
        timer.schedule(scanner, 0, 1000 * intervalSeconds);
		logger.debug(Log.INIT,"end start");		
	}
	
	@Override
	public void stop() {
		logger.info(Log.FINISH, "begin stop");
		int waitSec = profile.getPropertyInt("shutdown_seconds", 30);
		boolean terminated = false;
		// shutdownNow will send an interrupt to all threads
		workerPool.shutdownNow();
		try {
			terminated = workerPool.awaitTermination(waitSec, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			terminated = false;
		}
		if (terminated) {
			logger.info(Log.FINISH, "Shutdown Successful");
		}
		else {
			logger.warn("Some threads failed to terminate");
		}
		logger.info("end stop");		
	}
	
	@Override
	public void destroy() {
		
	}


}
