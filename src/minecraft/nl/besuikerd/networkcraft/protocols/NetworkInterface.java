package nl.besuikerd.networkcraft.protocols;

import java.util.Map;

public interface NetworkInterface {
	
	Map<MacAddress, MacAddress> Link ;

	public void getMacAddresses();
	
	public void setMacAddress();

}
