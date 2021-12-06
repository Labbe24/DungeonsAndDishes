package Framework;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.dungeonsanddishes.game.TextBox;

public class SetTextAction extends Action {
    protected String textToDisplay;

    public SetTextAction(String t) {
        textToDisplay = t;
    }

    public boolean act(float dt) {
        TextBox tb = (TextBox)target;
        tb.setText(textToDisplay);
        return true;
    }
}
