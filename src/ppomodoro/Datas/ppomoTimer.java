package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class ppomoTimer {
	private static ppomoTimer singleton = new ppomoTimer();
	private static List<TimerTicListener> listeners = new ArrayList<TimerTicListener>();
	
	public int data = 0;
	
	private boolean isRunning = false;
	private static int second = 0;
	private int ppomoCount = 0;
	private long startTime = 0;
	
	private java.util.Timer timer = new Timer();
	
	public ppomoTimer() {
	}
	
	public void addListener(TimerTicListener newListener) {
		listeners.add(newListener);
	}
	
	// stackoverflow.com/questions/16128423/how-to-update-the-label-box-every-2-seconds-in-java-fx
	private void startTic() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ppomoTimer.second += 1;
						
						for(TimerTicListener t : listeners) {
							t.timeTic(ppomoTimer.second);
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
		
	public static ppomoTimer getInstance() {
		return ppomoTimer.singleton;
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
	
	public boolean isRunning() {
		return this.isRunning;
	}	
}
