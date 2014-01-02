package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.gui.texture.ElementStylerTexture;
import nl.besuikerd.core.gui.texture.ITexturedBackground;
import nl.besuikerd.core.gui.texture.StateFulBackground;
import nl.besuikerd.core.gui.texture.Texture;
import nl.besuikerd.core.gui.texture.TexturedBackground;
import nl.besuikerd.core.gui.texture.TexturedBackgroundScroller;
import nl.besuikerd.core.utils.MathUtils;

public class ElementScrollBar extends ElementContainer {

	protected ElementScrollBar self = this;
	
	protected ElementContainer containerScroller;
	protected ElementScroller scroller;
	protected ElementButton buttonUp;
	protected ElementButton buttonDown;
	
	/**
	 * number between 0 and 1 that defines how far down this scrollbar is scrolled
	 */
	protected double progress;

	public ElementScrollBar(int width) {
		this.layout = new VerticalLayout();
		this.buttonUp = new ElementButton(width, 11, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(Texture.ARROW_UP)){
			@Override
			protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
				setProgress(progress - 0.05);
				return true;
			}
			
		};
		this.buttonDown = new ElementButton(width, 11, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(Texture.ARROW_DOWN)){
			@Override
			protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
				setProgress(progress + 0.05);
				
				return true;
			}
		};
		this.containerScroller = new ElementScrollerContainer(width, 0);
		this.scroller = new ElementScroller(width, 5);
		this.containerScroller.add(scroller);
		add(buttonUp);
		add(containerScroller);
		add(buttonDown);
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		containerScroller.height = parent.getHeight() - buttonUp.getHeight() - buttonDown.getHeight() - parent.paddingTop - parent.paddingBottom;
		scroller.height = containerScroller.height / 4;
		super.draw(root, mouseX, mouseY);
	}
	
	public ElementScrollBar progress(double progress){
		this.progress = progress;
		return this;
	}

	private class ElementScrollerContainer extends ElementStyledContainer {
		

		public ElementScrollerContainer(int width, int height) {
			super(width, height, TexturedBackground.CONTAINER);
			padding(0); //no padding is fine
		}

		@Override
		protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
			if(self.isEnabled() && !MathUtils.inRange2D(x, scroller.x, scroller.x + scroller.width, y, scroller.y, scroller.y + scroller.height)){ // only do this when scroller isn't in range
				setProgress((double) (y - scroller.height / 2) / (height - scroller.height));
			}
			return true;
		}
	}

	private class ElementScroller extends Element {
			public ElementScroller(int width, int height) {
				super(width, height);
			}

		@Override
		protected boolean onMove(ElementRootContainer root, int x, int y, int which) {
			if(self.isEnabled()){
				setProgress((double) y / (parent.height - scroller.height));
			}
			return true;
		}
		
		@Override
		public void dimension(ElementRootContainer root) {
			super.dimension(root);
			this.y = (int) Math.round(progress * (parent.height - height));
		}
		
		@Override
		public void draw(ElementRootContainer root, int mouseX, int mouseY) {
			ITexturedBackground bg = self.isEnabled() ? isLeftClicked() ? TexturedBackgroundScroller.ACTIVATED : TexturedBackgroundScroller.NORMAL : TexturedBackgroundScroller.DISABLED;
			drawBackgroundFromTextures(bg);
			super.draw(root, mouseX, mouseY);
		}
	}
	
	public boolean setProgress(double progress){
		double old = this.progress;
		this.progress = progress > 1 ? 1 : progress < 0 ? 0 : progress;
		if(old != this.progress){
			onProgressChange(old, this.progress);
			return true;
		}
		return false;
	}
	
	public void onProgressChange(double old, double progress){
		
	}
	
	@Override
	public boolean onScrolled(ElementRootContainer root, int x, int y, int amount) {
		boolean consumed = setProgress(progress + (-0.1 * (amount / 120))); //consume input if progress changed
		BLogger.debug("consume: %b", consumed);
		return consumed;
//		return setProgress(progress + (-0.1 * (amount / 120))); //consume input if progress changed
	}
}
