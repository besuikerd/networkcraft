package nl.besuikerd.core.gui.element;

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
	 * number between 0 and 1 that defines how far down this scrollbar is
	 * scrolled
	 */
	protected double progress;

	public ElementScrollBar(int width) {
		this.layout = new VerticalLayout();
		this.buttonUp = new ElementScrollerButton(width, 11, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(Texture.ARROW_UP)) {
			@Override
			protected void changeProgress() {
				setProgress(progress - 0.05);
			}
		};
		this.buttonDown = new ElementScrollerButton(width, 11, StateFulBackground.SOLID_BUTTON, new ElementStylerTexture(Texture.ARROW_DOWN)) {
			@Override
			protected void changeProgress() {
				setProgress(progress + 0.05);
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
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		containerScroller.height = parent.getHeight() - buttonUp.getHeight() - buttonDown.getHeight() - parent.paddingTop - parent.paddingBottom;
		scroller.height = containerScroller.height / 4;
		super.draw(parent, mouseX, mouseY, root);
	}

	public ElementScrollBar progress(double progress) {
		this.progress = progress;
		return this;
	}

	private abstract class ElementScrollerButton extends ElementButton {

		public static final long THRESHOLD_SCROLLBUTTON_PRESSED = 150l;
		private long oldTime;

		public ElementScrollerButton(int width, int i, StateFulBackground solidButton, ElementStylerTexture elementStylerTexture) {
			super(width, i, solidButton, elementStylerTexture);
		}

		@Override
		protected boolean onPressed(ElementContainer parent, int x, int y, int which) {
			changeProgress();
			this.oldTime = System.currentTimeMillis() + 2*THRESHOLD_SCROLLBUTTON_PRESSED;
			return false;
		}

		abstract void changeProgress();

		@Override
		public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
			if (isLeftClicked() && System.currentTimeMillis() - this.oldTime > THRESHOLD_SCROLLBUTTON_PRESSED) {
				this.oldTime = System.currentTimeMillis();
				changeProgress();
			}
			super.draw(parent, mouseX, mouseY, root);
		}
	}

	private class ElementScrollerContainer extends ElementStyledContainer {

		public ElementScrollerContainer(int width, int height) {
			super(width, height, TexturedBackground.CONTAINER);
			padding(0); //no padding is fine
		}

		@Override
		protected boolean onPressed(ElementContainer parent, int x, int y, int which) {
			if (self.isEnabled() && !MathUtils.inRange2D(x, y, scroller.x, scroller.x + scroller.width, scroller.y, scroller.y + scroller.height)) { // only do this when scroller isn't in range
				setProgress((double) (y - scroller.height / 2) / (height - scroller.height));
			}
			return false;
		}
	}

	private class ElementScroller extends Element {
		public ElementScroller(int width, int height) {
			super(width, height);
		}

		@Override
		protected boolean onMove(ElementContainer parent, int x, int y, int which) {
			if (self.isEnabled()) {
				setProgress((double) y / (parent.height - scroller.height));
			}
			return true;
		}

		@Override
		public void dimension(ElementContainer parent, ElementContainer root) {
			super.dimension(parent, root);
			this.y = (int) Math.round(progress * (parent.height - height));
		}

		@Override
		public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
			ITexturedBackground bg = self.isEnabled() ? isLeftClicked() ? TexturedBackgroundScroller.ACTIVATED : TexturedBackgroundScroller.NORMAL : TexturedBackgroundScroller.DISABLED;
			drawBackgroundFromTextures(bg);
			super.draw(parent, mouseX, mouseY, root);
		}
	}

	public void setProgress(double progress) {
		double old = progress;
		this.progress = progress > 1 ? 1 : progress < 0 ? 0 : progress;
		onProgressChange(old, this.progress);
	}

	public void onProgressChange(double old, double progress) {

	}

	@Override
	public void onScrolled(ElementContainer parent, int x, int y, int amount) {
		setProgress(progress + (-0.1 * (amount / 120)));
	}
}
