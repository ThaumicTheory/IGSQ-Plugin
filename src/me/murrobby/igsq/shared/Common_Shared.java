package me.murrobby.igsq.shared;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Common_Shared 
{
	public static <T> List<T> getBetween(List<T> args, int leftSide,int rightSide)
    {
        return args.subList(leftSide, rightSide);
    }
	
	 //Appends a value to the end of array
	@Deprecated
    public static String[] append(String[] array, String value)
    {
    	String[] arrayAppended = new String[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    @Deprecated
    public static String[] arrayAppend(String[] array, String[] array2) 
    {
    	String[] appendedArray = array;
    	for (String string : array2) 
    	{
    		appendedArray = append(appendedArray,string);
    	}
    	return appendedArray;
    }
    @Deprecated
	public static String[] depend(String[] array, int location)
    {
        String[] arrayDepended = new String[array.length-1];
        int hitRemove = 0;
        for (int i = 0;i < array.length;i++)
        {
            if(location != i){
                arrayDepended[i-hitRemove] = array[i];
            }
            else{
                hitRemove++;
            }
        }
        return arrayDepended;
    }
    /**
     * Removes all text before a given character. If the character is not found the whole string is returned.
     * @apiNote used in commands to remove the command identifier minecraft: etc
     * @return <b>String</b>
     */
    public static String removeBeforeCharacter(String string,char target) 
    {
    	Boolean targetFound = false;
    	char[] charArray = string.toCharArray();
    	String rebuiltString = "";
    	for(int i = 0;i < string.length();i++) 
    	{
    		if(!targetFound)
    		{
    			if(charArray[i] == target) targetFound = true;
    		}
    		else rebuiltString += charArray[i];
    	}
    	if(targetFound) return rebuiltString;
    	else return string;
    }
    /**
     * Removes null and replaces it with an empty String
     * @apiNote used for sensitive data queries
     * @return <b>String</b>
     */
    public static String removeNull(String string) 
    {
    	if(string == null) return "";
    	else return string;
    }
    
	public static String convertArgs(List<String> args,String seperator) 
	{
		return String.join(" ", args);
	}
	
	public static String getTimeFuture(String timeString) 
	{
		String[] array = timeString.split("[tsmhdwMy]");
		if(array.length != 2) return getTime();
		long timeComponent = Long.valueOf(array[0]);
		String unitComponent = array[1];
		
		LocalDateTime time = LocalDateTime.now();
		switch(unitComponent) 
		{
			case "t":
				return getTime(time.plus(timeComponent/50, ChronoUnit.MILLIS));
			case "s":
				return getTime(time.plus(timeComponent, ChronoUnit.SECONDS));
			case "m":
				return getTime(time.plus(timeComponent, ChronoUnit.MINUTES));
			case "h":
				return getTime(time.plus(timeComponent, ChronoUnit.HOURS));
			case "d":
				return getTime(time.plus(timeComponent, ChronoUnit.DAYS));
			case "w":
				return getTime(time.plus(timeComponent, ChronoUnit.WEEKS));
			case "M":
				return getTime(time.plus(timeComponent, ChronoUnit.MONTHS));
			case "y":
				return getTime(time.plus(timeComponent, ChronoUnit.YEARS));
			default: //Future scheduler may run early as it can only calculate a delay in ticks! It is advised to not work in milliseconds
				return getTime(time.plus(timeComponent, ChronoUnit.MILLIS));
		}
	}
	
	public static String getTime() 
	{
		return getTime(LocalDateTime.now());
	}
	public static String getTime(LocalDateTime time) 
	{
		return time.get(ChronoField.HOUR_OF_DAY) + 
		"-" + time.get(ChronoField.MINUTE_OF_HOUR) + 
		"-" + time.get(ChronoField.SECOND_OF_MINUTE) + 
		"-" + time.get(ChronoField.MILLI_OF_SECOND)/50 + 
		"-" + time.get(ChronoField.DAY_OF_MONTH) + 
		"-" + time.get(ChronoField.MONTH_OF_YEAR) + 
		"-" + time.get(ChronoField.YEAR);
	}
	public static LocalDateTime getTime(String time) 
	{
		String[] timeComponents = time.split("-");
		return LocalDateTime.of(Integer.parseInt(timeComponents[6]),
				Integer.parseInt(timeComponents[5]), Integer.parseInt(timeComponents[4]),
				Integer.parseInt(timeComponents[0]), Integer.parseInt(timeComponents[1]),
				Integer.parseInt(timeComponents[2]),Integer.parseInt(timeComponents[3])*20000);
		
	}
	
}
