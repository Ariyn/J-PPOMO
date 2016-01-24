package ppomodoro.Datas;

public interface TimerTicListener {
	void timeTic(int second);
	void timerEnd();
	void timerStart(int completeSecond, String type, int time);
}