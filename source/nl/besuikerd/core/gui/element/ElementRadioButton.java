package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.event.Trigger;
import nl.besuikerd.core.gui.styler.ElementStylerTexture;
import nl.besuikerd.core.gui.texture.Texture;

public class ElementRadioButton extends Element{
	protected boolean toggled;
	protected ElementRadioGroup group;
	
	public ElementRadioButton() {
		super(16, 16);
		this.styler = new ElementStylerTexture(Texture.RADIO_INNER){
			public void style(Element e) {
				if(toggled){
					super.style(e);
				}
			};
		};
	}
	
	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
		drawTextureCentered(Texture.RADIO_OUTER);
	}
	
	public void toggle(ElementRootContainer root, boolean toggle){
		this.toggled = toggle;
		onToggled(root, toggle);
	}
	
	public void toggleOn(ElementRootContainer root){
		toggle(root, true);
	}
	
	public void toggleOff(ElementRootContainer root){
		toggle(root, false);
	}
	
	protected void onToggled(ElementRootContainer root, boolean toggle){
		doTrigger(Trigger.TOGGLED, root, toggle);
	}
	
	@Override
	protected void onReleased(ElementRootContainer root, int x, int y, int which) {
		super.onReleased(root, x, y, which);
		group.radioSelected(root, this);
	}
}
