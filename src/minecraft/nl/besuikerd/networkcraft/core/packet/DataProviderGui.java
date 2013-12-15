package nl.besuikerd.networkcraft.core.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import nl.besuikerd.networkcraft.gui.GuiBase;
import nl.besuikerd.networkcraft.gui.element.ElementContainer;

public class DataProviderGui implements IDataProvider{

	private GuiBase base;
	
	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		ElementContainer root = base.getRoot();
	}

	@Override
	public void readData(DataInputStream dis) throws IOException {
	}

}
