package nl.besuikerd.networkcraft.core.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.network.packet.Packet;

public interface IProcessData{
	public void read(ByteArrayDataInput in);
	public void write(ByteArrayDataOutput out);
}
