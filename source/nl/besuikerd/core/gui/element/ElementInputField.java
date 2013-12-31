package nl.besuikerd.core.gui.element;

import java.awt.event.KeyEvent;
import java.util.Currency;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.gui.texture.TexturedBackground;
import nl.besuikerd.core.utils.MathUtils;

public class ElementInputField extends ElementContainer {

	protected ElementContainer inputFieldContainer;

	public ElementInputField(int width, String text) {
		this.layout = new HorizontalLayout();
		this.inputFieldContainer = new ElementInputFieldContainer(width, fontRenderer.FONT_HEIGHT + 4);
		inputFieldContainer.add(new ElementInputLabel(2, 2, text));
		add(inputFieldContainer);
	}
	
	public ElementInputField(int width){
		this.layout = new HorizontalLayout();
		this.inputFieldContainer = new ElementInputFieldContainer(width, fontRenderer.FONT_HEIGHT + 4);
		inputFieldContainer.add(new ElementInputLabel(2, 2));
		add(inputFieldContainer);
	}

	private class ElementInputFieldContainer extends ElementStyledContainer {

		public ElementInputFieldContainer(int width, int height) {
			super(width, height, TexturedBackground.CONTAINER);
			padding(0); //no padding is fine
		}
	}

	private class ElementInputLabel extends Element {

		public static final int COLOR_DISABLED = 0xFF404040;
		public static final long FLICKER_TIME = 800L;
		public static final long THRESHHOLD_KEY_DOWN = 100L;

		protected StringBuilder textBuilder;
		
		protected long oldTime;
		protected boolean blockInput = false;		
		protected int color = 0xFFFFFFFF;
		protected int maxWidth = 0;
		protected int cursorOffset = 0;
		protected boolean invalidateMaxWidth = false;

		protected int pressedKey = -1;
		protected char lastChar;

		public ElementInputLabel(int x, int y) {
			super(x, y, 0, 0);
			this.textBuilder = new StringBuilder();
			this.height = fontRenderer.FONT_HEIGHT + 2;
		}
		
		public ElementInputLabel(int x, int y, String text){
			this(x, y);
			textBuilder.append(text);
			invalidateMaxWidth = true;
			cursorOffset = textBuilder.length();
			width = fontRenderer.getStringWidth(textBuilder.toString()) + 2;
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

		@Override
		protected boolean keyTyped(char key, int code) {
			if (!this.isEnabled() && code != Keyboard.KEY_RETURN) {
				return false;
			}
			
			if(invalidateMaxWidth){
				if (fontRenderer.getStringWidth(textBuilder.toString() + "W") + 2 > maxWidth) {
					blockInput = true;
				}
				invalidateMaxWidth = false;
			}
			
			if (Character.isLetterOrDigit(key)) {
				if (fontRenderer.getStringWidth(textBuilder.toString() + "W") + 2 > maxWidth) {
					blockInput = true;
				}
				if (!blockInput) {
					textBuilder.insert(cursorOffset, key);
					cursorOffset++;
				}
			} else {
				switch (code) {
				case Keyboard.KEY_BACK:
					if (cursorOffset > 0) {
						textBuilder.deleteCharAt(cursorOffset - 1);
						blockInput = false;
						cursorOffset--;
					}
					break;
				case Keyboard.KEY_DELETE:
					if (cursorOffset < textBuilder.length()) {
						textBuilder.deleteCharAt(cursorOffset);
					}
					break;
				case Keyboard.KEY_LEFT:
					if (cursorOffset > 0) {
						cursorOffset--;
					}
					break;
				case Keyboard.KEY_RIGHT:
					if (cursorOffset < textBuilder.length()) {
						cursorOffset++;
					}
					break;
				case Keyboard.KEY_RETURN:
					this.toggle(ENABLED);
				default:
					return false;
				}
			}

			if (fontRenderer.getStringWidth(textBuilder.toString() + "W") + 2 > maxWidth) {
				blockInput = true;
			}
			width = fontRenderer.getStringWidth(textBuilder.toString()) + 2;
			return true;
		}

		@Override
		public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
			this.maxWidth = parent.width - 4;

			//TODO prefer to move this to another function that gets called often but cant find one
			if (pressedKey != -1 && System.currentTimeMillis() - THRESHHOLD_KEY_DOWN > oldTime) {
				oldTime = System.currentTimeMillis();
				this.keyTyped(lastChar, pressedKey);
			}

			fontRenderer.drawString(textBuilder.toString(), absX() + ((width - fontRenderer.getStringWidth(textBuilder.toString())) / 2), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), this.isEnabled() ? color : COLOR_DISABLED);

			if (this.isEnabled()) {
				if (System.currentTimeMillis() - FLICKER_TIME > oldTime) {
					if (System.currentTimeMillis() - 2 * FLICKER_TIME > oldTime) {
						oldTime = System.currentTimeMillis();
					}
				} else {
					fontRenderer.drawString("|", absX() + fontRenderer.getStringWidth(textBuilder.substring(0, cursorOffset)), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), blockInput ? COLOR_DISABLED : color);
				}
			}
		}
	}

}
