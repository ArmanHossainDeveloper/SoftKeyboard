package app.keyboard.soft;

import android.view.KeyEvent;
import android.view.View;

public class PhysicalKeyboard {
    MiniKeyboard ims;
    public KeyeventSender ks;
    KeyTouchManager touchManager;

    int actionClick = 0, actionAltXKey = 1, actionShiftXKey = 2, actionShiftXAltXKey = 3, actionCtrlXKey = 4, actionCtrlXAltXKey = 5, actionCtrlXShiftXKey = 6, actionCtrlXShiftXAltXKey = 7;
    boolean isCtrl, isShift, isAlt;

    boolean isSoftKeyboardVisible = true;

    boolean isEnglish = true;
    String lastKey = "";
    String dueKar = "";

    public PhysicalKeyboard(MiniKeyboard ims) {
        this.ims = ims;

    }

    public void init() {
        ks = ims.keyeventSender;
        touchManager = ims.touchManager;

    }

    void toggleSoftKeyboard() {
        isSoftKeyboardVisible = !isSoftKeyboardVisible;
        if (isSoftKeyboardVisible) ims.keyboardContainer.setVisibility(View.VISIBLE);
        else ims.keyboardContainer.setVisibility(View.GONE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_RECENT_APPS:
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_POWER:
                return false;

            case KeyEvent.KEYCODE_CTRL_LEFT:
            case KeyEvent.KEYCODE_CTRL_RIGHT:
                isCtrl = true;
                break;

            case KeyEvent.KEYCODE_SHIFT_LEFT:
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                isShift = true;
                break;

            case KeyEvent.KEYCODE_ALT_LEFT:
            case KeyEvent.KEYCODE_ALT_RIGHT:
                isAlt = true;
                break;


        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                sendKey("1", "!", "১", keyCode);
                break;

            case KeyEvent.KEYCODE_2:
                sendKey("2", "@", "২", keyCode);
                break;

            case KeyEvent.KEYCODE_3:
                sendKey("3", "#", "৩", keyCode);
                break;

            case KeyEvent.KEYCODE_4:
                sendKey("4", "$", "৪", "৳", keyCode);
                break;

            case KeyEvent.KEYCODE_5:
                sendKey("5", "%", "৫", keyCode);
                break;

            case KeyEvent.KEYCODE_6:
                sendKey("6", "^", "৬", keyCode);
                break;

            case KeyEvent.KEYCODE_7:
                sendKey("7", "&", "৭", "ঁ", keyCode);
                break;

            case KeyEvent.KEYCODE_8:
                sendKey("8", "*", "৮", keyCode);
                break;

            case KeyEvent.KEYCODE_9:
                sendKey("9", "(", "৯", keyCode);
                break;

            case KeyEvent.KEYCODE_0:
                sendKey("0", ")", "০", keyCode);
                break;

            case KeyEvent.KEYCODE_BACKSLASH:
                sendKey("\\", "|", "ৎ", "ঃ", keyCode);
                break;

            case KeyEvent.KEYCODE_Q:
                sendKey("q", "Q", "ঙ", "ং", keyCode);
                break;

            case KeyEvent.KEYCODE_W:
                sendKey("w", "W", "য", "য়", keyCode);
                break;

            case KeyEvent.KEYCODE_E:
                sendKey("e", "E", "ড", "ঢ", keyCode);
                break;

            case KeyEvent.KEYCODE_R:
                sendKey("r", "R", "প", "ফ", keyCode);
                break;

            case KeyEvent.KEYCODE_T:
                sendKey("t", "T", "ট", "ঠ", keyCode);
                break;

            case KeyEvent.KEYCODE_Y:
                sendKey("y", "Y", "চ", "ছ", keyCode);
                break;

            case KeyEvent.KEYCODE_U:
                sendKey("u", "U", "জ", "ঝ", keyCode);
                break;

            case KeyEvent.KEYCODE_I:
                sendKey("i", "I", "হ", "ঞ", keyCode);
                break;

            case KeyEvent.KEYCODE_O:
                sendKey("o", "O", "গ", "ঘ", keyCode);
                break;

            case KeyEvent.KEYCODE_P:
                sendKey("p", "P", "ড়", "ঢ়", keyCode);
                break;

            case KeyEvent.KEYCODE_A:
                sendProblemKey("a", "A", "ৃ", "\u200D", keyCode);
                break;

            case KeyEvent.KEYCODE_S:
                sendProblemKey("s", "S", "ু", "ূ", keyCode);
                break;

            case KeyEvent.KEYCODE_D:
                sendProblemKey("d", "D", "ি", "ী", keyCode);
                break;

            case KeyEvent.KEYCODE_F:
                sendProblemKey("f", "F", "া", "অ", keyCode);
                break;

            case KeyEvent.KEYCODE_G:
                sendProblemKey("g", "G", "্", "।", keyCode);
                break;

            case KeyEvent.KEYCODE_H:
                sendKey("h", "H", "ব", "ভ", keyCode);
                break;

            case KeyEvent.KEYCODE_J:
                sendKey("j", "J", "ক", "খ", keyCode);
                break;

            case KeyEvent.KEYCODE_K:
                sendKey("k", "K", "ত", "থ", keyCode);
                break;

            case KeyEvent.KEYCODE_L:
                sendKey("l", "L", "দ", "ধ", keyCode);
                break;

            case KeyEvent.KEYCODE_Z:
                sendKey("z", "Z", "্র", "\u200D্য", keyCode);
                break;

            case KeyEvent.KEYCODE_X:
                sendProblemKey("x", "X", "ও", "ৗ", keyCode);
                break;

            case KeyEvent.KEYCODE_C:
                sendProblemKey("c", "C", "ে", "ৈ", keyCode);
                break;

            case KeyEvent.KEYCODE_V:
                sendKey("v", "V", "র", "ল", keyCode);
                break;

            case KeyEvent.KEYCODE_B:
                sendKey("b", "B", "ন", "ণ", keyCode);
                break;

            case KeyEvent.KEYCODE_N:
                sendKey("n", "N", "স", "ষ", keyCode);
                break;

            case KeyEvent.KEYCODE_M:
                sendKey("m", "M", "ম", "শ", keyCode);
                break;

            case KeyEvent.KEYCODE_RECENT_APPS:
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_POWER:
                return false;
            case KeyEvent.KEYCODE_CTRL_LEFT:
            case KeyEvent.KEYCODE_CTRL_RIGHT:
                isCtrl = false;
                break;

            case KeyEvent.KEYCODE_SHIFT_LEFT:
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                isShift = false;
                break;

            case KeyEvent.KEYCODE_ALT_LEFT:
            case KeyEvent.KEYCODE_ALT_RIGHT:
                isAlt = false;
                break;

            case KeyEvent.KEYCODE_SCROLL_LOCK:
                toggleSoftKeyboard();
                break;

            case KeyEvent.KEYCODE_TAB:
                sentTab();
                break;

            case KeyEvent.KEYCODE_CAPS_LOCK:
                isEnglish = !isEnglish;
                break;

            default:
                sendPrimaryKey(keyCode);
        }
        return true;
    }

    void sentTab() {
        int keyCode = KeyEvent.KEYCODE_TAB;
        int actionType = getActionType();
        if (actionType == actionClick) {
            ks.sendText("\t");
        }
        else if (actionType == actionShiftXKey) {
            ks.sendShiftXKey(keyCode);
        }
        else if (actionType == actionCtrlXShiftXKey) {
            ks.sendCtrlXShiftXKey(keyCode);
        }
        else if (actionType == actionAltXKey) {
            ks.sendAltXKey(keyCode);
        }
        else if (actionType == actionCtrlXKey) {
            ks.sendCtrlXKey(keyCode);
        }
        if (!isEnglish) lastKey = dueKar = "";
    }

    void sendPrimaryKey(int keyCode) {
        int actionType = getActionType();
        if (actionType == actionClick) {
            ks.sendKey(keyCode);
        }
        else if (actionType == actionShiftXKey) {
            ks.sendShiftXKey(keyCode);
        }
        else if (actionType == actionCtrlXShiftXKey) {
            ks.sendCtrlXShiftXKey(keyCode);
        }
        else if (actionType == actionAltXKey) {
            ks.sendAltXKey(keyCode);
        }
        else if (actionType == actionCtrlXKey) {
            ks.sendCtrlXKey(keyCode);
        }
        if (!isEnglish) {
            lastKey = dueKar = "";
        }
    }

    void sendKey(String primaryKey, String shiftXPrimary, String banglaKey, int keyCode) {
        sendKey(primaryKey, shiftXPrimary, banglaKey, shiftXPrimary, keyCode);

    }

    void sendProblemKey(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, int keyCode) {
        boolean shouldIgnoreAltC = keyCode == 31;
        int actionType = getActionType();
        if (actionType == actionClick) {
            if (isEnglish) ks.sendText(primaryKey);
            else {
                if ("্".equals(banglaKey)) {
                    ks.sendText(banglaKey);
                    switch (lastKey) {
                        case "ি":
                        case "ে":
                        case "ৈ":
                            dueKar = lastKey;
                    }
                    lastKey = banglaKey;
                    return;
                }
                boolean shouldSendKey = true;
                boolean isHosonto = "্".equals(lastKey);
                dueKar = "";
                switch (banglaKey) {
                    case "ৃ":
                        if (isHosonto) {
                            lastKey = "ঋ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ু":
                        if (isHosonto) {
                            lastKey = "উ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ি":
                        lastKey = banglaKey;
                        shouldSendKey = false;
                        if (isHosonto) {
                            lastKey = "ই";
                            ks.sendReplacement(lastKey, 1);
                        } else dueKar = banglaKey;
                        break;
                    case "া":
                        if ("অ".equals(lastKey)) {
                            lastKey = "আ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ে":
                        lastKey = banglaKey;
                        shouldSendKey = false;
                        if (isHosonto) {
                            lastKey = "এ";
                            ks.sendReplacement(lastKey, 1);
                        } else dueKar = banglaKey;
                        break;
                }
                if (shouldSendKey) {
                    ks.sendText(banglaKey);
                    lastKey = banglaKey;
                }
            }
        }
        else if (actionType == actionShiftXKey) {
            if (isEnglish) ks.sendText(shiftXPrimary);
            else {
                boolean shouldSendKey = true;
                boolean isHosonto = "্".equals(lastKey);
                dueKar = "";

                switch (shiftXBangla) {
                    case "ূ":
                        if (isHosonto) {
                            lastKey = "ঊ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ী":
                        if (isHosonto) {
                            lastKey = "ঈ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ৗ":
                        if (isHosonto) {
                            lastKey = "ঔ";
                            ks.sendReplacement(lastKey, 1);
                            shouldSendKey = false;
                        }
                        break;
                    case "ৈ":
                        lastKey = shiftXBangla;
                        shouldSendKey = false;
                        if (isHosonto) {
                            lastKey = "ঐ";
                            ks.sendReplacement(lastKey, 1);
                        } else dueKar = shiftXBangla;
                        break;
                }
                if (shouldSendKey) {
                    ks.sendText(shiftXBangla);
                    lastKey = shiftXBangla;
                }
            }
        }

        else if (actionType == actionCtrlXShiftXKey) ks.sendCtrlXShiftXKey(keyCode);
        else if (actionType == actionAltXKey) {
            if (shouldIgnoreAltC) return;
            ks.sendAltXKey(keyCode);

        }
        else if (actionType == actionCtrlXKey) {
            ks.sendCtrlXKey(keyCode);

        }


    }

    void sendKey(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, int keyCode) {
        int actionType = getActionType();
        if (actionType == actionClick) {
            if (isEnglish) ks.sendText(primaryKey);
            else {
                if (!"".equals(dueKar)) {
                    if ("্".equals(lastKey)) {
                        String text = "্" + banglaKey + dueKar;
                        ks.sendReplacement(text, 2);
                    } else {
                        ks.sendText(banglaKey + dueKar);
                    }
                    lastKey = dueKar;
                    dueKar = "";
                } else {
                    ks.sendText(banglaKey);
                    lastKey = banglaKey;
                }
            }
        }
        else if (actionType == actionShiftXKey) {
            if (isEnglish) ks.sendText(shiftXPrimary);
            else {
                if (!"".equals(dueKar)) {
                    if ("্".equals(lastKey)) {
                        String text = "্" + shiftXBangla + dueKar;
                        ks.sendReplacement(text, 2);
                    } else {
                        ks.sendText(shiftXBangla + dueKar);
                    }
                    lastKey = dueKar;
                    dueKar = "";

                } else {
                    ks.sendText(shiftXBangla);
                    lastKey = shiftXBangla;
                }
            }
        }
        else if (actionType == actionCtrlXShiftXKey) {
            ks.sendCtrlXShiftXKey(keyCode);

        }
        else if (actionType == actionAltXKey) {
            ks.sendAltXKey(keyCode);
        }
        else if (actionType == actionCtrlXKey) {
            ks.sendCtrlXKey(keyCode);
        }
    }

    int getActionType(){
        return (isCtrl ? 4 : 0) | (isShift ? 2 : 0) | (isAlt ? 1 : 0);

    }

}



















