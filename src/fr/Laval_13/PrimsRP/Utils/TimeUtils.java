package fr.Laval_13.PrimsRP.Utils;


public class TimeUtils {
	
	public static int convertTicktoMinutes(long time){
		return convertTicktoSecond(time) / 60;
	}
	
	private static int convertTicktoSecond(long time){
		return (int) (time / 20);
	}

}
