package nl.besuikerd.networkcraft.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.besuikerd.networkcraft.generic.NetworkCraft;
import cpw.mods.fml.common.FMLCommonHandler;
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
	
	public static void log(Level level, Object msg, Object... params){
		logger.log(level, String.format("%s|%s", FMLCommonHandler.instance().getEffectiveSide(), String.format(msg.toString(), params)));
	}
	
	public static void log(Level level, Object msg){
		logger.log(level, String.format("%s|%s", FMLCommonHandler.instance().getEffectiveSide(), msg));
	}
	
	public static void warn(Object msg, Object... params){
		log(Level.WARNING, msg, params);
	}
	
	public static void warn(Object msg){
		log(Level.WARNING, msg);
	}
	
	public static void error(Object msg, Object... params){
		log(Level.SEVERE, msg, params);
	}
	
	public static void error(Object msg){
		log(Level.SEVERE, msg);
	}
	
	public static void info(Object msg, Object... params){
		log(Level.INFO, msg, params);
	}
	
	public static void info(Object msg){
		log(Level.INFO, msg);
	}
	
	public static void debug(Object msg, Object... params){
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
