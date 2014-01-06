package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.event.Trigger;
import nl.besuikerd.core.gui.styler.ElementStylerTexture;
import nl.besuikerd.core.gui.texture.Texture;
import nl.besuikerd.core.gui.texture.scalable.ScalableTexture;

public class ElementCheckbox extends Element{

        protected boolean checked;
        
        public ElementCheckbox(boolean checked) {
                super(16, 16);
                this.styler = new ElementStylerTexture(Texture.CHECK_MARK){
                        @Override
                        public void style(Element e) {
                                if(isChecked()){
                                        super.style(e);
                                }
                        }
                };
                this.checked = checked;
        }

        public void onChecked(ElementRootContainer root, boolean checked){
                doTrigger(Trigger.CHECKED, root, checked);
        }
        
        public boolean isChecked() {
                return checked;
        }
        
        @Override
        public void draw(ElementRootContainer root, int mouseX, int mouseY) {
                drawBackgroundFromTextures(ScalableTexture.CONTAINER_INVERSE);
                super.draw(root, mouseX, mouseY);
        }
        
        @Override
        protected void onReleased(ElementRootContainer root, int x, int y, int which) {
                this.checked = !checked;
                onChecked(root, checked);
                super.onPressed(root, x, y, which);
        }
}