package nl.besuikerd.networkcraft.tileentity;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.element.ElementButton;
import nl.besuikerd.core.gui.element.ElementContainer;
import nl.besuikerd.core.gui.element.ElementRootContainer;
import nl.besuikerd.core.gui.element.ElementScrollContainer;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.tileentity.TileEntityBesu;

public class TileEntityTestGui extends TileEntityBesu{
	public static class Gui extends GuiBase{

		public Gui(ContainerBesu container) {
			super(container);
		}
		
		@Override
		public void init() {
			root.padding(5)
			.layout(new HorizontalLayout(5, 0))
			
//			.add(new ElementButton(70, 30, "testButton"))
			
			.add(new ElementScrollContainer(50, new ElementContainer()
				.layout(new HorizontalLayout())
				.paddingRight(25)
				.add(new ElementScrollContainer(200, new ElementContainer()
					.layout(new HorizontalLayout())
					.add(new ElementScrollContainer(400, new ElementContainer()
						.layout(new HorizontalLayout())
						.add(new ElementButton(50, 1250, "bla"))
					))
					
				))
			))
//			
			;
		}
	}
}
