package nl.besuikerd.networkcraft.core.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import nl.besuikerd.networkcraft.core.Constants;
import nl.besuikerd.networkcraft.core.exception.NetworkCraftException;
import nl.besuikerd.networkcraft.core.exception.NetworkCraftProtocolException;
import nl.besuikerd.networkcraft.core.utils.ReflectUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Nicker
 * 
 * 
 * Packets have the following structure:
 * 
 * [id(8)][payload(variable)]
 *
 */
public abstract class PacketNC implements IProcessData{
	
	public static final String CHANNEL = Constants.PACKET_CHANNEL;
	
	private static final BiMap<Byte, Class<? extends PacketNC>> ids;
	
	protected byte id;

	static{
		ImmutableBiMap.Builder<Byte, Class<? extends PacketNC>> builder = ImmutableBiMap.builder();
		ids = builder.build();
		registerPacketTypes();
	}
	
	//register our packet types	
	private static void registerPacketTypes(){
		
	}
	
	protected PacketType type;
	protected EntityPlayer player;
	
	public static PacketNC fromId(byte id) throws NetworkCraftException{
		Class<? extends PacketNC> cls = ids.get(id);
		if(cls == null){
			throw new NetworkCraftProtocolException(String.format("invalid packet id %d", id));
		}
		
		PacketNC packet = ReflectUtils.newInstance(cls);
		if(packet == null){
			throw new NetworkCraftException(String.format("failed to instantiate new packet, please check if %s has a default constructor", cls.getName()));
		}
		return packet;
	}
	
	public PacketType getType() {
		return type;
	}
	
	public Packet build(){
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeByte(id);
		write(out);
		return PacketDispatcher.getPacket(CHANNEL, out.toByteArray());
	}
	
	public abstract void onReceive(Side side);
}
