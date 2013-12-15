package nl.besuikerd.networkcraft.core.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IDataProvider {
	public void writeData(DataOutputStream dos) throws IOException;
	public void readData(DataInputStream dis) throws IOException;
}
