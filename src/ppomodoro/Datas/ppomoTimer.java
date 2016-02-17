package ppomodoro.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class PpomoTimer {
	private static PpomoTimer singleton = new PpomoTimer();
	private static List<TimerTicListener> listeners = new ArrayList<TimerTicListener>();
		
	private boolean isRunning = false;
	private int second = 0;
	
	private ProgramManager pm = ProgramManager.getInstance();
	
//	private TimerTask runTask; 
	
	private int completeSecond = 6;
	private String ppomoType = "";
	
	private java.util.Timer timer;
	
	public PpomoTimer() {
		
	}
	
	public int getCompleteSecond() {
		return this.completeSecond;
	}
	public void setCompleteSecond(int minute) {
		// TODO: should not change when on timer.
		if(!isRunning) {
			this.completeSecond = minute * 60;
		}
	}
	
	public void addListener(TimerTicListener newListener) {
		ArrayList<TimerTicListener> tl = new ArrayList<TimerTicListener>();
		for(TimerTicListener t :listeners) {
			if(t.getClass() == newListener.getClass()) {
				tl.add(t);
			}
		}
		
		listeners.add(newListener);
		newListener.timerStart(completeSecond, ppomoType, second);
		for(TimerTicListener t :tl) {
			listeners.remove(t);
		}
	}
	
	public void removeListener(TimerTicListener oldListener) {
		boolean check = listeners.remove(oldListener);
		System.out.println(check);
	}
	
	// stackoverflow.com/questions/16128423/how-to-update-the-label-box-every-2-seconds-in-java-fx
	private void startTic() {
		for(TimerTicListener t : listeners) {
			t.timerStart(this.completeSecond, this.ppomoType, this.second);
		}
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					PpomoTimer p = PpomoTimer.getInstance();
					
					@Override
					public void run() {		
						for(TimerTicListener t : listeners) {
							t.timeTic(this.p.second);
						}
						
						if(this.p.completeSecond <= this.p.second) {
							System.out.println("Done");
							System.out.println(listeners.size());
							
							for(TimerTicListener t: listeners) {
								System.out.println(t);
								t.timerEnd();
							}
							this.p.stopPpomo(true);
						} else {
							this.p.second += 1;
						}
					}
				});
			}
		}, 0, 1000);
	}
	
	private void stopTic() {
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
		if(!isRunning) {
			int minute = pm.setPpomoStart();
			this.setCompleteSecond(minute);
			this.ppomoType = pm.getThisPpomoType();
			
			// test ninja code!
//			this.completeSecond = minute;
					
			
			isRunning = true;
			startTic();
		}
	}
	
	public void stopPpomo(boolean succeed) {
		if(isRunning) {
			isRunning = false;
			
			pm.setPpomoEnd(succeed);
			stopTic();
			
			second = 0;
		}
	}
	
	public boolean isRunning() {
		return this.isRunning;
	}	
}
