package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.Orientation;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.gui.texture.ElementStylerTexture;
import nl.besuikerd.core.gui.texture.ITexturedBackground;
import nl.besuikerd.core.gui.texture.StateFulBackground;
import nl.besuikerd.core.gui.texture.Texture;
import nl.besuikerd.core.gui.texture.TexturedBackground;
import nl.besuikerd.core.gui.texture.TexturedBackgroundVerticalScroller;
import nl.besuikerd.core.utils.MathUtils;

public class ElementScrollBar extends ElementContainer {

	protected ElementScrollBar self = this;
	
	protected ElementContainer containerScroller;
	protected ElementScroller scroller;
	protected ElementButton buttonUp;
	protected ElementButton buttonDown;
	
	protected Orientation orientation;
	
	/**
	 * number between 0 and 1 that defines how far down this scrollbar is scrolled
	 */
	protected double progress;

	public ElementScrollBar(int dimension, Orientation orientation) {
		boolean vertical = orientation == Orientation.VERTICAL;
		
		this.layout = vertical ? new VerticalLayout() : new HorizontalLayout();
		this.orientation = orientation;
		
		int buttonDimension = 11;
		
		this.buttonUp = new ElementButton(vertical ? dimension : buttonDimension, vertical ? buttonDimension : dimension, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(vertical ? Texture.ARROW_UP : Texture.ARROW_LEFT)){
			@Override
			protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
				self.onScrolled(root, x, y, 120);
				return true;
			}
		};
		this.buttonDown = new ElementButton(vertical ? dimension : buttonDimension, vertical ? buttonDimension : dimension, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(vertical ? Texture.ARROW_DOWN : Texture.ARROW_RIGHT)){
			@Override
			protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
				self.onScrolled(root, x, y, -120);
				return true;
			}
		};
		this.containerScroller = new ElementScrollerContainer(vertical ? dimension : 0, vertical ? 0 : dimension);
		this.scroller = new ElementScroller(vertical ? dimension : 0, vertical ? 0 : dimension);
		this.containerScroller.add(scroller);
		add(buttonUp);
		add(containerScroller);
		add(buttonDown);
	}
	
	public int getScrollerSize(){
		return orientation == Orientation.VERTICAL ? containerScroller.height / 4 : containerScroller.width / 4;
	}
	
	public ElementScrollBar(int width){
		this(width, Orientation.VERTICAL);
	}
	
	@Override
	public void dimension(ElementRootContainer root) {
		super.dimension(root);
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		
		if(orientation == Orientation.VERTICAL){
			containerScroller.height = parent.getHeight() - buttonUp.getHeight() - buttonDown.getHeight() - parent.paddingTop - parent.paddingBottom;
			scroller.height = getScrollerSize();
		} else{
			containerScroller.width = parent.getWidth() - buttonUp.getWidth() - buttonDown.getWidth() - parent.paddingLeft - parent.paddingRight;
			scroller.width = getScrollerSize();
		}
		
		super.draw(root, mouseX, mouseY);
	}
	
		private class ElementScrollerContainer extends ElementStyledContainer {
		

		public ElementScrollerContainer(int width, int height) {
			super(width, height, TexturedBackground.CONTAINER);
			padding(0); //no padding is fine
		}

		@Override
		protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
			if(self.isEnabled() && !MathUtils.inRange2D(x, scroller.x, scroller.x + scroller.width, y, scroller.y, scroller.y + scroller.height)){ // only do this when scroller isn't in range
				
				if(orientation == Orientation.VERTICAL){
					setProgress((double) (y - scroller.height / 2) / (height - scroller.height));
				} else{
					setProgress((double) (x - scroller.width / 2) / (width - scroller.width));
				}
			}
			return true;
		}
	}

	private class ElementScroller extends ElementStatefulBackground {
		public ElementScroller(int width, int height) {
			super(orientation == Orientation.VERTICAL ? StateFulBackground.SCROLLER_VERTICAL : StateFulBackground.SCROLLER_HORIZONTAL, width, height);
		}

		@Override
		protected boolean onMove(ElementRootContainer root, int x, int y, int which) {
			if(self.isEnabled()){
				if(orientation == Orientation.VERTICAL){
					setProgress((double) y / (parent.height - scroller.height));
				} else{
					setProgress((double) x / (parent.width - scroller.width));
				}
			}
			return true;
		}
		
		@Override
		public void update(ElementRootContainer root) {
			statefulBackground = orientation == Orientation.VERTICAL ? StateFulBackground.SCROLLER_VERTICAL : StateFulBackground.SCROLLER_HORIZONTAL;
		}
		
		@Override
		public void dimension(ElementRootContainer root) {
			super.dimension(root);
			
			if(orientation == Orientation.VERTICAL){
				this.y = (int) Math.round(progress * (parent.height - height));
			} else{
				this.x = (int) Math.round(progress * (parent.width - width));
			}
		}
		
		@Override
		public void draw(ElementRootContainer root, int mouseX, int mouseY) {
			ITexturedBackground bg = self.isEnabled() ? isLeftClicked() ? TexturedBackgroundVerticalScroller.ACTIVATED : TexturedBackgroundVerticalScroller.NORMAL : TexturedBackgroundVerticalScroller.DISABLED;
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
		return setProgress(progress + (-0.1d * (amount / 120))); //consume input if progress changed
	}
}
