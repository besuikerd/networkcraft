package nl.besuikerd.core.gui.texture;


public enum StateFulBackground implements IStateFulBackground<ElementState>{
	BUTTON{
		@Override
		public ITexturedBackground backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return TexturedBackgroundButton.DISABLED;
				case HOVERING:
					return TexturedBackgroundButton.HOVERING;
				case ACTIVATED:
					return TexturedBackgroundButton.ACTIVATED;
				default:
					return TexturedBackgroundButton.NORMAL;
			}
		}
	},
	
	SOLID_BUTTON{
		@Override
		public ITexturedBackground backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return TexturedBackgroundSolidButton.DISABLED;
				case ACTIVATED:
					return TexturedBackgroundSolidButton.ACTIVATED;
				default:
					return TexturedBackgroundSolidButton.NORMAL;
			}
		}
	},
	
	SCROLLER_VERTICAL{
		@Override
		public ITexturedBackground backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return TexturedBackgroundVerticalScroller.DISABLED;
				case ACTIVATED:
					return TexturedBackgroundVerticalScroller.ACTIVATED;
				default:
					return TexturedBackgroundVerticalScroller.NORMAL;
			}
		}
	},
	
	SCROLLER_HORIZONTAL{
		public ITexturedBackground backgroundForState(ElementState state) {
			switch(state){
				case DISABLED:
					return TexturedBackgroundHorizontalScroller.DISABLED;
				case ACTIVATED:
					return TexturedBackgroundHorizontalScroller.ACTIVATED;
				default:
					return TexturedBackgroundHorizontalScroller.NORMAL;
			}
		}
	}
	;

	@Override
	public ITexturedBackground backgroundForState(ElementState state) {
		throw new UnsupportedOperationException("inner classes should provide an implementation");
	}
}
