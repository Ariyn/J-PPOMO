package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class PpomoTimer {
	private static PpomoTimer singleton = new PpomoTimer();
	private static List<TimerTicListener> listeners = new ArrayList<TimerTicListener>();
	
	public int data = 0;
	
	private boolean isRunning = false;
	private static int second = 0;
	private int ppomoCount = 0;
	private long startTime = 0;
	
	private java.util.Timer timer = new Timer();
	
	public PpomoTimer() {
	}
	
	public void addListener(TimerTicListener newListener) {
		listeners.add(newListener);
	}
	public void removeListener(TimerTicListener oldListener) {
		boolean check = listeners.remove(oldListener);
		System.out.println(check);
	}
	
	// stackoverflow.com/questions/16128423/how-to-update-the-label-box-every-2-seconds-in-java-fx
	private void startTic() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						PpomoTimer.second += 1;
						
						// TODO: change this end time to variable
						if(6 <= PpomoTimer.second) {
							for(TimerTicListener t: listeners) {
								t.timerEnd();
							}
							PpomoTimer.getInstance().stopPpomo();
						}
						
						for(TimerTicListener t : listeners) {
							t.timeTic(PpomoTimer.second);
						}
					}
				});
			}
		}, 0, 1000);
	}
	
	private void stopTic() {
		// TODO: check done or failed.
		this.ppomoCount += 1;
		timer.cancel();
	}
		
	public static PpomoTimer getInstance() {
		return PpomoTimer.singleton;
	}
	
	public int getSecond() {
		return second;
	}
	
	// TODO: change start and stop ppomo to static functions
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
	
	public boolean isRunning() {
		return this.isRunning;
	}	
}
