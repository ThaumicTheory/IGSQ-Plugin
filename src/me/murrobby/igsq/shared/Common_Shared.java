package me.murrobby.igsq.shared;

import java.util.List;

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
	
}
