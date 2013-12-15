package nl.besuikerd.networkcraft.core.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.exception.NetworkCraftException;
import nl.besuikerd.networkcraft.core.exception.NetworkCraftProtocolException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandlerNC implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
		EntityPlayer ePlayer = (EntityPlayer) player;
		try {
			PacketNC p = PacketNC.fromId(in.readByte());
			p.player = ePlayer;
			p.read(in);
			p.onReceive(ePlayer.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
		} catch(NetworkCraftProtocolException e){
			if(player instanceof EntityPlayerMP){
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				playerMP.playerNetServerHandler.kickPlayerFromServer(e.getMessage());
				NCLogger.warn("player %s was kicked from the server, reason: %s", playerMP.username, e.getMessage());
			}
		} catch (NetworkCraftException e) {
			NCLogger.warn("unable to process packet, reason: %s", e.getMessage());
		}
	}

}
