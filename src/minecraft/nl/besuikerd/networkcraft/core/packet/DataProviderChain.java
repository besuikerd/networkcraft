package nl.besuikerd.networkcraft.core.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class DataProviderChain implements IDataProvider{
	
	private List<IDataProvider> providers;
	
	
	public DataProviderChain() {
		this(new ArrayList<IDataProvider>());
	}
	
	public DataProviderChain(List<IDataProvider> providers) {
		this.providers = providers;
	}

	public DataProviderChain chain(IDataProvider provider){
		providers.add(provider);
		return this;
	}
	
	@Override
	public void readData(DataInputStream dis) throws IOException {
		for(IDataProvider p : providers){
			p.readData(dis);
		}
	}
	
	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		for(IDataProvider p : providers){
			p.writeData(dos);
		}
	}
}
