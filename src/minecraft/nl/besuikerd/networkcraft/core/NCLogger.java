package nl.besuikerd.networkcraft.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.besuikerd.networkcraft.generic.NetworkCraft;
import cpw.mods.fml.common.FMLLog;

public class NCLogger {
	
	private static final Logger logger = Logger.getLogger("NetworkCraft");
	
	public static void init(){
		logger.setParent(FMLLog.getLogger());
		info("Starting NetworkCraft");
		if(NetworkCraft.DEBUG_MODE){
			info("DEBUG_MODE is enabled. More debug messages will be printed");
		}
	}
	
	public static void log(Level level, String msg, Object... params){
		logger.log(level, String.format(msg, params));
	}
	
	public static void log(Level level, String msg){
		logger.log(level, msg);
	}
	
	public static void warn(String msg, Object... params){
		log(Level.WARNING, msg, params);
	}
	
	public static void warn(String msg){
		log(Level.WARNING, msg);
	}
	
	public static void error(String msg, Object... params){
		log(Level.SEVERE, msg, params);
	}
	
	public static void error(String msg){
		log(Level.SEVERE, msg);
	}
	
	public static void info(String msg, Object... params){
		log(Level.INFO, msg, params);
	}
	
	public static void info(String msg){
		log(Level.INFO, msg);
	}
	
	public static void debug(String msg, Object... params){
		if(NetworkCraft.DEBUG_MODE){
			info(msg, params);
		}
	}
	
	public static void debug(String msg){
		if(NetworkCraft.DEBUG_MODE){
			info(msg);
		}
	}
}
