package com.besuikerd.networkcraft.tileentity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiBase;
import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementButton;
import com.besuikerd.core.gui.element.ElementCheckbox;
import com.besuikerd.core.gui.element.ElementContainer;
import com.besuikerd.core.gui.element.ElementInputField;
import com.besuikerd.core.gui.element.ElementList;
import com.besuikerd.core.gui.element.ElementProgressBar;
import com.besuikerd.core.gui.element.ElementRadioButton;
import com.besuikerd.core.gui.element.ElementRootContainer;
import com.besuikerd.core.gui.element.ElementScrollContainer;
import com.besuikerd.core.gui.element.RadioGroup;
import com.besuikerd.core.gui.element.adapter.BaseElementAdapter;
import com.besuikerd.core.gui.element.adapter.ButtonElementAdapter;
import com.besuikerd.core.gui.event.Event;
import com.besuikerd.core.gui.event.EventAction;
import com.besuikerd.core.gui.event.Trigger;
import com.besuikerd.core.gui.layout.Alignment;
import com.besuikerd.core.gui.layout.HorizontalLayout;
import com.besuikerd.core.gui.layout.Orientation;
import com.besuikerd.core.gui.layout.VerticalLayout;
import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.tileentity.TileEntityBesu;

public class TileEntityTestGui extends TileEntityBesu{
	public static class Gui extends GuiBase{
		
		private List<String> items;
		
		public Gui(ContainerBesu container) {
			super(container);
		}
		
		@Override
		public void init() {
			RadioGroup group = new RadioGroup();
			BaseElementAdapter<String> base = new ButtonElementAdapter("bla", "another", "button", "under", "eachother"){
				@Override
				public Element createElementAt(String data, int index) {
					return super.createElementAt(data, index).trigger(Trigger.PRESSED, Event.RESET).action(Event.RESET, EventAction.DISABLE);
				}
			};
			ElementList list = new ElementList(base);
			items = base.synchronizedList();
			
			root
			.add(new ElementContainer().layout(new HorizontalLayout())
				.add(new ElementButton("+").trigger(Trigger.PRESSED, Event.ADD))
				.add((new ElementButton("-").trigger(Trigger.PRESSED, Event.REMOVE))
			))
			
			.add(new ElementScrollContainer(100)
				.add(new ElementScrollContainer(200)
					.add(new ElementScrollContainer(400).add(list))
					
					
				)
			)
			
			.add(ElementProgressBar.progressBarBurn().trigger(Trigger.UPDATE, "updateBurn"))
			.add(ElementProgressBar.progressBarArrow().trigger(Trigger.UPDATE, "updateArrow"))
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
		
		int counter;
		
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
			
			if(name.equals("updateBurn")){
				if(counter++ > 10){
					ElementProgressBar progressBar = (ElementProgressBar) e;
					progressBar.progress((progressBar.getProgress() + 1) % progressBar.getMax());
					counter = 0;
				}
			} else if(name.equals("updateArrow")){
				ElementProgressBar progressBar = (ElementProgressBar) e;
				progressBar.progress(Math.min(progressBar.getProgress() + 5, progressBar.getMax()) % progressBar.getMax());
			}
		}
	}
}
