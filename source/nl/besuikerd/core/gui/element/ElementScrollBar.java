package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.utils.Tuple;

public class ElementScrollBar extends ElementContainer {

	protected ElementContainer containerScroller;
	protected ElementScroller scroller;
	protected ElementButton buttonUp;
	protected ElementButton buttonDown;

	public ElementScrollBar() {
		this.layout = new VerticalLayout();
		this.buttonUp = new ElementButton("^");
		this.buttonDown = new ElementButton("v");
		this.containerScroller = new ElementScrollerContainer(buttonUp.getWidth(), 0);
		this.scroller = new ElementScroller(buttonUp.getWidth(), 0);
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

	private class ElementScrollerContainer extends ElementContainer {
		

		public ElementScrollerContainer(int width, int height) {
			super(width, height);
		}

		@Override
		protected boolean onPressed(ElementContainer parent, int x, int y, int which) {
			scroller.y = y - scroller.getHeight() / 2;
			BLogger.debug(y);
			return false;
		}
	}

	private class ElementScroller extends Element {
		
		
		
		public ElementScroller(int width, int height) {
			super(width, height);
		}

		@Override
		protected boolean onMove(ElementContainer parent, int x, int y, int which) {
			this.x = x;
			this.y = y;
			return true;
		}
		
		@Override
		public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
			ScrollerBackground bg = isEnabled() ? isLeftClicked() ? ScrollerBackground.ACTIVATED : ScrollerBackground.NORMAL : ScrollerBackground.DISABLED;
			drawBackgroundFromTextures(bg.texBackground, bg.texEdgeTop, bg.texEdgeRight, bg.texEdgeBottom, bg.texEdgeLeft, bg.texCornerTL, bg.texCornerTR, bg.texCornerBR, bg.texCornerBL);
			super.draw(parent, mouseX, mouseY, root);
		}
		
		
	}
	
	private enum ScrollerContainerBackground{
//		NORMAL(new Tuple())
		;
		
		protected Tuple texEdgeTop;
		protected Tuple texEdgeRight;
		protected Tuple texEdgeBottom;
		protected Tuple texEdgeLeft;

		protected Tuple texCornerTL;
		protected Tuple texCornerTR;
		protected Tuple texCornerBR;
		protected Tuple texCornerBL;

		protected Tuple texBackground;

		private ScrollerContainerBackground(Tuple texBackground, Tuple texEdgeTop, Tuple texEdgeRight, Tuple texEdgeBottom, Tuple texEdgeLeft, Tuple texCornerTL, Tuple texCornerTR, Tuple texCornerBR, Tuple texCornerBL) {
			this.texBackground = texBackground;
			this.texEdgeTop = texEdgeTop;
			this.texEdgeRight = texEdgeRight;
			this.texEdgeBottom = texEdgeBottom;
			this.texEdgeLeft = texEdgeLeft;
			this.texCornerTL = texCornerTL;
			this.texCornerTR = texCornerTR;
			this.texCornerBR = texCornerBR;
			this.texCornerBL = texCornerBL;
		}
	}
	
	private enum ScrollerBackground{
		NORMAL(new Tuple(98, 1, 8, 2), new Tuple(98, 0, 8, 2), new Tuple(106, 2, 2, 11), new Tuple(98, 13, 8, 2), new Tuple(96, 2, 2, 11), new Tuple(96, 0, 2, 2), new Tuple(106, 0, 2, 2), new Tuple(106, 13, 2, 2), new Tuple(96, 13, 2, 2)),
		ACTIVATED(new Tuple(110, 1, 8, 2), new Tuple(110, 0, 8, 2), new Tuple(118, 2, 2, 11), new Tuple(110, 13, 8, 2), new Tuple(108, 2, 2, 11), new Tuple(108, 0, 2, 2), new Tuple(118, 0, 2, 2), new Tuple(118, 13, 2, 2), new Tuple(108, 13, 2, 2)),
		DISABLED(new Tuple(122, 1, 8, 2), new Tuple(122, 0, 8, 2), new Tuple(130, 2, 2, 11), new Tuple(122, 13, 8, 2), new Tuple(120, 2, 2, 11), new Tuple(120, 0, 2, 2), new Tuple(130, 0, 2, 2), new Tuple(130, 13, 2, 2), new Tuple(120, 13, 2, 2))
		;
		
		protected Tuple texEdgeTop;
		protected Tuple texEdgeRight;
		protected Tuple texEdgeBottom;
		protected Tuple texEdgeLeft;

		protected Tuple texCornerTL;
		protected Tuple texCornerTR;
		protected Tuple texCornerBR;
		protected Tuple texCornerBL;

		protected Tuple texBackground;

		private ScrollerBackground(Tuple texBackground, Tuple texEdgeTop, Tuple texEdgeRight, Tuple texEdgeBottom, Tuple texEdgeLeft, Tuple texCornerTL, Tuple texCornerTR, Tuple texCornerBR, Tuple texCornerBL) {
			this.texBackground = texBackground;
			this.texEdgeTop = texEdgeTop;
			this.texEdgeRight = texEdgeRight;
			this.texEdgeBottom = texEdgeBottom;
			this.texEdgeLeft = texEdgeLeft;
			this.texCornerTL = texCornerTL;
			this.texCornerTR = texCornerTR;
			this.texCornerBR = texCornerBR;
			this.texCornerBL = texCornerBL;
			
		}
	}
}
