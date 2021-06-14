package thaumictheory.igsq.spigot.main;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import thaumictheory.igsq.spigot.Messaging;

public class LoggerHandler_Main extends Handler{

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(LogRecord arg0) 
	{
		boolean ignore = arg0 != null && arg0.getMessage().startsWith("[@] ");
		if(!ignore) 
		{
			if(arg0.getThrown() != null) 
			{
				Messaging.sendException(arg0.getThrown(), arg0.getMessage());
				return;
			}
			else if(arg0.getMessage() != null) 
			{
				Messaging.sendLog(Messaging.getLogColour(arg0.getLevel())+arg0.getMessage());
				return;
			}
		}
		
	}

}
