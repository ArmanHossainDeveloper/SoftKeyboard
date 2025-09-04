package app.keyboard.soft;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

public class KeyeventSender {

    int modCtrl = KeyEvent.KEYCODE_CTRL_LEFT;
    int modShift = KeyEvent.KEYCODE_SHIFT_LEFT;
    int modAlt = KeyEvent.KEYCODE_ALT_LEFT;
    int metaCtrl = KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON;
    int metaShift = KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON;
    int metaAlt = KeyEvent.META_ALT_ON | KeyEvent.META_ALT_LEFT_ON;

    InputMethodService ims;
    public InputConnection ic;
    public KeyeventSender(InputMethodService ims){
        this.ims = ims;
    }

    public void sendChar(char key) {
        ims.sendKeyChar(key);
    }

    public void sendReplacement(String text, int i) {
        ic.deleteSurroundingText(i, 0);
        ic.commitText(text, 1);
    }

    public void sendDel() {
        ic.deleteSurroundingText(1, 0);
    }
    public void sendText(String text) {
        ic.commitText(text, 1);
    }
    public void sendKey(int key) {
        sendKeyDown(key);
        sendKeyUp(key);
    }
    public void sendKey(int key, int metaState) {
        sendKeyDown(key, metaState);
        sendKeyUp(key, metaState);
    }

    public void sendShiftXKey(int keyCode) {
        sendKeyCombination(modShift, keyCode, metaShift);
    }
    public void sendCtrlXShiftXKey(int keyCode) {
        int[] modifiers = new int[]{modCtrl, modShift};
        int metaState = metaCtrl | metaShift;
        sendKeyCombination(modifiers, keyCode, metaState);
    }
    public void sendAltXKey(int keyCode) {
        sendKeyCombination(modAlt, keyCode, metaAlt);
    }
    public void sendCtrlXKey(int keyCode) {
        sendKeyCombination(modCtrl, keyCode, metaCtrl);
    }


    public void sendKeyCombination(int modifier, int key, int metaState) {
        sendKeyDown(modifier);
        sendKey(key, metaState);
        sendKeyUp(modifier);
        ic.clearMetaKeyStates(metaState);
    }
    public void sendKeyCombination(int[] modifiers, int key, int metaState) {
        for (int modifier : modifiers) {sendKeyDown(modifier);}
        sendKey(key, metaState);
        for (int modifier : modifiers) {sendKeyUp(modifier);}
        ic.clearMetaKeyStates(metaState);
    }

    public void sendKeyDown(int keyCode) {
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
    }
    public void sendKeyUp(int keyCode) {
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
    }
    public void sendKeyDown(int keyCode, int metaState) {
        sendEvent(0, keyCode, metaState);
    }
    public void sendKeyUp(int keyCode, int metaState) {
        sendEvent(1, keyCode, metaState);
    }
    public void sendEvent(int action, int keyCode, int metaState) {
        KeyEvent event = new KeyEvent(0, 0, action, keyCode, 0, metaState);
        ic.sendKeyEvent(event);
    }

}















