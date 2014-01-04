package nl.besuikerd.networkcraft.tileentity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementButton;
import nl.besuikerd.core.gui.element.ElementContainer;
import nl.besuikerd.core.gui.element.ElementInputField;
import nl.besuikerd.core.gui.element.ElementList;
import nl.besuikerd.core.gui.element.ElementProgressBar;
import nl.besuikerd.core.gui.element.ElementRootContainer;
import nl.besuikerd.core.gui.element.ElementScrollContainer;
import nl.besuikerd.core.gui.element.adapter.BaseElementAdapter;
import nl.besuikerd.core.gui.element.adapter.ButtonElementAdapter;
import nl.besuikerd.core.gui.event.EventAction;
import nl.besuikerd.core.gui.event.Event;
import nl.besuikerd.core.gui.event.Trigger;
import nl.besuikerd.core.gui.layout.Alignment;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.Orientation;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.tileentity.TileEntityBesu;

public class TileEntityTestGui extends TileEntityBesu{
	public static class Gui extends GuiBase{
		
		private List<String> items;
		
		public Gui(ContainerBesu container) {
			super(container);
		}
		
		@Override
		public void init() {
			
			BaseElementAdapter<String> base = new ButtonElementAdapter("bla", "another", "button", "under", "eachother"){
				@Override
				public Element createElementAt(String data, int index) {
					return super.createElementAt(data, index).trigger(Trigger.PRESSED, Event.RESET).action(Event.RESET, EventAction.REMOVE_ELEMENT);
				}
			};
			ElementList list = new ElementList(base);
			items = base.synchronizedList();
			
			root
			.add(new ElementContainer().layout(new HorizontalLayout())
				.add(new ElementButton("+").trigger(Trigger.PRESSED, Event.ADD))
				.add((new ElementButton("-").trigger(Trigger.PRESSED, Event.REMOVE))
			))
			
			.add(new ElementScrollContainer(100).add(list))
			
			;
//			.add(new ElementScrollContainer(50, new ElementContainer()
//				.layout(new HorizontalLayout())
//				.paddingRight(25)
//				.add(new ElementScrollContainer(200, new ElementContainer()
//					.layout(new HorizontalLayout())
					
//					
//				))
//			))
//			
			;
		}
		
		@Override
		public void onEvent(String name, Element e, Object... args) {
			switch(Event.lookup(name)){
				case ADD:
					items.add(String.format("add: (%d,%d,%d)", args[0], args[1], args[2]));
					break;
				case REMOVE:
					items.remove(items.size() - 1);
					break;
				default:
					break;
			}
		}
	}
}
