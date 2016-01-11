package ppomodoro;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Timer {
	private static Timer singleton = new Timer();
	
	public int data = 0;
	
	private boolean isRunning = false;
	private static int second = 0;
	private int ppomoCount = 0;
	private long startTime = 0;
	
	private static Runnable timeTic = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			Timer.second += 1;
			System.out.println(Timer.second);
		}
	};
	
	private ScheduledExecutorService service = null;
	private ScheduledFuture<?> ticService = null;
	
	public Timer() {
		service = Executors.newSingleThreadScheduledExecutor();
	}
	
	private void startTic() {
		ticService = service.scheduleWithFixedDelay(timeTic, 1, 1, TimeUnit.SECONDS);
	}
	
	private void stopTic() {
		ticService.cancel(true);
	}
		
	public static Timer getInstance() {
		return Timer.singleton;
	}
	
	public void startNewPpomo() {
		startTime = System.currentTimeMillis();
		isRunning = true;
		startTic();
	}
	
	public void stopPpomo() {
		ppomoCount += 1;
		isRunning = false;
		stopTic();
	}
	
}
