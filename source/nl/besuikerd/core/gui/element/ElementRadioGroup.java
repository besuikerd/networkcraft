package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.Orientation;
import nl.besuikerd.core.gui.layout.VerticalLayout;

public class ElementRadioGroup extends ElementContainerDelegate{

	protected int selectedIndex = 0;
	
	public ElementRadioGroup(Orientation orientation){
		super(0, 0);
		this.container = new ElementContainer();
		this.container.layout = orientation.isVertical() ? new VerticalLayout() : new HorizontalLayout();
	}
	
	public void radioSelected(ElementRootContainer root, ElementRadioButton radio){
		int radioIndex = -1;
		if((radioIndex = container.indexOf(radio)) != -1){
			if(selectedIndex < container.getElementCount()){
				((ElementRadioButton)container.elementAt(selectedIndex)).toggleOff(root);
			}
			radio.toggleOn(root);
			this.selectedIndex = radioIndex;
		}
	}

	public ElementRadioGroup add(ElementRadioButton button){
		button.group = this;
		container.add(button);
		return this;
	}
	
	public ElementRadioGroup add(ElementRadioButton... buttons){
		for(ElementRadioButton b : buttons){
			add(b);
		}
		return this;
	}
	
	public void remove(ElementRadioButton radio){
		radio.group = null;
		container.remove(radio);
		if(selectedIndex > container.getElementCount() - 1){
			selectedIndex = container.getElementCount() - 1;
		}
	}
	
}
