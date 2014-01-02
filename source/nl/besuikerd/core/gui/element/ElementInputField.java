package nl.besuikerd.core.gui.element;

import java.util.regex.Pattern;

import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.texture.TexturedBackground;

import org.lwjgl.input.Keyboard;

public class ElementInputField extends ElementContainer {

	protected ElementContainer inputFieldBackgroundContainer;
	protected ElementContainer inputFieldContainer;
	protected ElementViewport viewPort;
	protected ElementInputLabel inputFieldLabel;
	protected boolean hasFocus;
	protected boolean releaseTypeFocus;
	protected boolean forceTypeFocus;

	public ElementInputField(int width, String text) {
		this(width, text, "");
	}
	
	public ElementInputField(int width){
		this(width, "", "");
	}
	
	public ElementInputField(int width, String text, String regex){
		this.layout = new HorizontalLayout();
		this.inputFieldBackgroundContainer = new ElementInputFieldContainer(width, fontRenderer.FONT_HEIGHT + 4);
		this.inputFieldContainer = new ElementContainer(0,0,width, fontRenderer.FONT_HEIGHT + 4);
		this.inputFieldLabel = new ElementInputLabel(2, 2, width, text, regex);
		inputFieldContainer.add(inputFieldLabel);
		this.viewPort = new ElementViewport(fontRenderer.FONT_HEIGHT + 4,  inputFieldContainer);
		inputFieldBackgroundContainer.add(viewPort);
		add(inputFieldBackgroundContainer);
	}

	private class ElementInputFieldContainer extends ElementStyledContainer {

		public ElementInputFieldContainer(int width, int height) {
			super(width, height, TexturedBackground.CONTAINER);
			padding(0); //no padding is fine
		}
	}	
	
	public Element setFocus(boolean focus){
		if(focus){
			forceTypeFocus = true;
		}else{
			releaseTypeFocus = true;
		}
		return this;
	}
	
	@Override
	protected boolean onPressed(ElementContainer parent, int x, int y, int which) {
		forceTypeFocus = true;
		return inputFieldLabel.placeCursor(x, y);
	}

	private class ElementInputLabel extends Element {

		public static final int COLOR_DISABLED = 0xFF404040;
		public static final int COLOR_UNFOCUSSED = 0xFFF0F0F0;
		public static final long FLICKER_TIME = 800L;
		public static final long THRESHHOLD_KEY_DOWN = 100L;

		protected StringBuilder textBuilder;
		protected String acceptedChars = "";
		
		protected long oldTime;
		protected boolean blockInput = false;		
		protected int color = 0xFFFFFFFF;
		protected int maxWidth = 0;
		protected int cursorOffset = 0;
		protected int hideOffset = 1;
		protected int widthDiff = 0;

		protected int pressedKey = -1;
		protected char lastChar;

		public ElementInputLabel(int x, int y, int width, String text){
			super(x, y, 0, 0);
			this.textBuilder = new StringBuilder();
			this.height = fontRenderer.FONT_HEIGHT + 2;
			textBuilder.append(text);
		}
		
		/**
		 * constructor
		 * @param x	
		 * @param y	
		 * @param width	
		 * @param acceptedChars	Regex to accept more chars than only numbers and digits
		 * @param text Initial text
		 */
		public ElementInputLabel(int x, int y, int width, String acceptedChars, String text){
			this(x, y, width, text);
			this.acceptedChars = acceptedChars;
		}

		@Override
		public boolean handleKeyboardInput() {
			if (Keyboard.getEventKeyState()) {
				oldTime = System.currentTimeMillis() + 3 * THRESHHOLD_KEY_DOWN;
				pressedKey = Keyboard.getEventKey();
				lastChar = Keyboard.getEventCharacter();
			} else {
				pressedKey = -1;
			}
			return super.handleKeyboardInput();
		}
		
		public boolean placeCursor(int x, int y){
			if(this.isEnabled()){
				int i = 0;
				int previous = Integer.MAX_VALUE;
				int current = Integer.MAX_VALUE;
				//TODO needs to be finetuned after viewport is implemented right!
				if(!hasFocus){
					widthDiff = 0;
				}
				while((current = fontRenderer.getStringWidth(textBuilder.substring(0,textBuilder.length()-i))+1) > x + widthDiff){
					previous = current;
					i++;
					if(i>=textBuilder.length()){
						current = Integer.MAX_VALUE;
						break;
					}
				}
				if(Math.abs((current-x))>=Math.abs((previous-x))){
					i--;
				}
				cursorOffset = textBuilder.length()-i;
				return true;
			}
			return false;
		}

		@Override
		protected boolean keyTyped(char key, int code, ElementContainer root) {
			if ((!this.isEnabled() && code != Keyboard.KEY_RETURN) || !hasFocus) {
				return false;
			}
			if (Character.isLetterOrDigit(key) || (acceptedChars != "" && Pattern.matches(acceptedChars, Character.toString(key)))) {
				if (!blockInput || fontRenderer.getStringWidth(textBuilder.toString() + key) + 2 < maxWidth) {
					textBuilder.insert(cursorOffset, key);
					cursorOffset++;
					if(cursorOffset == textBuilder.length() && fontRenderer.getStringWidth(textBuilder.toString())>widthDiff+maxWidth){
						widthDiff += fontRenderer.getStringWidth(Character.toString(key));
					}
				}
			} else {
				switch (code) {
				case Keyboard.KEY_BACK:
					if (cursorOffset > 0) {
						widthDiff -= fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset - 1)));
						if(widthDiff < 0){
							widthDiff = 0;
						}
						textBuilder.deleteCharAt(cursorOffset - 1);
						hideOffset = 1;
						cursorOffset--;
					}
					break;
				case Keyboard.KEY_DELETE:
					if (cursorOffset < textBuilder.length()) {
						textBuilder.deleteCharAt(cursorOffset);
						hideOffset = 1;
					}
					break;
				case Keyboard.KEY_LEFT:
					if (cursorOffset > 0) {
						cursorOffset--;
						if(fontRenderer.getStringWidth(textBuilder.substring(0,cursorOffset))<widthDiff){
							widthDiff -= fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset)));
						}
						if(widthDiff < 0){
							widthDiff = 0;
						}
					}
					break;
				case Keyboard.KEY_RIGHT:
					if (cursorOffset < textBuilder.length()) {
						cursorOffset++;
						if(fontRenderer.getStringWidth(textBuilder.substring(0,cursorOffset))>widthDiff+maxWidth){
							widthDiff += fontRenderer.getStringWidth(Character.toString(textBuilder.charAt(cursorOffset - 1)));
						}
					}
					break;
				case Keyboard.KEY_END:
					cursorOffset = textBuilder.length();
					widthDiff = fontRenderer.getStringWidth(textBuilder.toString())-maxWidth;
					if(widthDiff < 0){
						widthDiff = 0;
					}
					break;
				case Keyboard.KEY_HOME:
					cursorOffset = 0;
					widthDiff = 0;
					break;
				case Keyboard.KEY_TAB:
				case Keyboard.KEY_RETURN:
					releaseTypeFocus = true;
					break;
				default:
					return false;
				}
			}
			return true;
		}
		
		@Override
		public void update(ElementContainer parent, ElementContainer root, int mouseX, int mouseY) {
			if(forceTypeFocus || (root.getTypeFocus() == null && this.isEnabled() && !releaseTypeFocus)){
				root.setTypeFocus(this);
				forceTypeFocus = false;
			}
			if(this == root.getTypeFocus()){
				hasFocus = true;
				if(releaseTypeFocus){
					root.setTypeFocus(null);
					cursorOffset = textBuilder.length();
					widthDiff = fontRenderer.getStringWidth(textBuilder.toString())-maxWidth;
					if(widthDiff < 0){
						widthDiff = 0;
					}
					hasFocus = false;
				}
			}else{
				hasFocus = false;
			}
			releaseTypeFocus = false;
			
			if (pressedKey != -1 && System.currentTimeMillis() - THRESHHOLD_KEY_DOWN > oldTime) {
				oldTime = System.currentTimeMillis();
				this.keyTyped(lastChar, pressedKey, root);
			}
		}
		
		@Override
		public void dimension(ElementContainer parent, ElementContainer root) {
			this.maxWidth = parent.width - 2;
		}

		@Override
		public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
			if (this.isEnabled() && hasFocus) {
				if (System.currentTimeMillis() - FLICKER_TIME > oldTime) {
					if (System.currentTimeMillis() - 2 * FLICKER_TIME > oldTime) {
						oldTime = System.currentTimeMillis();
					}
				} else {
					fontRenderer.drawString("|", parent.absX() - widthDiff + fontRenderer.getStringWidth(textBuilder.substring(0, cursorOffset)) + 1, absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), 0xFF000000);
				}
			}else if(fontRenderer.getStringWidth(textBuilder.toString()) > maxWidth){
				while(fontRenderer.getStringWidth(textBuilder.substring(0,textBuilder.length()-hideOffset-1)+"...") > maxWidth){
					hideOffset++;
				}
				fontRenderer.drawString(textBuilder.substring(0,textBuilder.length()-hideOffset-1)+"...", parent.absX() + 2, absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), this.isEnabled() ?  COLOR_UNFOCUSSED : COLOR_DISABLED);
				return;
			}
			fontRenderer.drawString(textBuilder.toString(), parent.absX() - widthDiff + 2 , absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), this.isEnabled() ? hasFocus ? color : COLOR_UNFOCUSSED : COLOR_DISABLED);
		}
	}

}
