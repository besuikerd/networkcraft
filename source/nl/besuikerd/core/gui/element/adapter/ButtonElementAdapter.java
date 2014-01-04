package nl.besuikerd.core.gui.element.adapter;

import java.util.ArrayList;
import java.util.Arrays;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementButton;
import nl.besuikerd.core.gui.layout.Alignment;

public class ButtonElementAdapter extends BaseElementAdapter<String>{

	public ButtonElementAdapter(String... buttons){
		super(new ArrayList<String>(Arrays.asList(buttons)));
	}
	
	@Override
	public Element createElementAt(String data, int index) {
		return new ElementButton(data).align(Alignment.CENTER);
	}

}
