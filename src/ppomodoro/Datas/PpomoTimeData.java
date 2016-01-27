package ppomodoro.Datas;

import java.util.ArrayList;

public class PpomoTimeData {
	private ArrayList<Long> timeList = new ArrayList<Long>(); 
	private ArrayList<String> typeList = new ArrayList<String>();
	
	public final int start=0, end=1;
	
	private String type; // "ppomo" or "break" or "long break"
	private boolean result = false;
	
	public PpomoTimeData(String type) {
		this.type = type;
		
		this.timeList.add((long) 0);
		this.timeList.add((long) 0);
		
		this.typeList.add("start");
		this.typeList.add("end");
	}
	
	public String getType() {
		return type;
	}
	
	public long getTime(int type) {
		long retVal = -1;
		
		if(0 <= type && type < timeList.size())
			retVal = timeList.get(type);
		
		return retVal;
	}
	
	public void setTime(int type, long value) {
		if(0 <= type && type < timeList.size())
			timeList.set(type, value);
	}
	
	public void setResult(boolean success) {
		this.result = success;
	}
	public boolean getResult() {
		return this.result;
	}
}