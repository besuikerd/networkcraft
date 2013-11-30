package nl.besuikerd.networkcraft.protocols;

import java.util.Random;

public class MacAddress {

	private int mac;
	
	public MacAddress() {
		mac = new Random().nextInt();
	}
	
	public int getMac() {
		return mac;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < Integer.SIZE ; i+=4){
			if(i%8==0 && i!=0)
				builder.append(':');
			builder.append(Integer.toHexString((mac >> i) & 0xF));
		}
		return builder.reverse().toString().toUpperCase();		
	}
	
	public static void main(String args[]){
		System.out.println(0%8);
		MacAddress mac = new MacAddress();
		System.out.printf("mac number: %d equals to %s", mac.getMac(), mac.toString());
	}

}
