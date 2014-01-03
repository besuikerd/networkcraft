package nl.besuikerd.core.gui.element.adapter;

import java.util.Arrays;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementButton;

public class ButtonElementAdapter extends BaseElementAdapter<String>{

	public ButtonElementAdapter(String... buttons){
		super(Arrays.asList(buttons));
	}
	
	@Override
	public Element createElementAt(String data, int index) {
		return new ElementButton(data);
	}

}
