package app.keyboard.soft;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import arman.common.ui.touchlistener.GestureListener;

public class KeyTouchManager {

    /*int metaCtrlShift = metaCtrl | metaShift;
    int metaCtrlAlt = metaCtrl | metaAlt;
    int metaShiftAlt = metaShift | metaAlt;
    int metaMax = metaCtrl | metaShift | metaAlt;*/

    LinearLayout keyboardContainer;
    PhysicalKeyboard physicalKeyboard;
    PopupWindow popupWindow;
    View popupView;
    int popupOffset = 250;
    MiniKeyboard ims;
    public KeyeventSender ks;
    Drawable[] touchIndicators = new Drawable[3];

    boolean isEnglish = true, isShiftHoldDown = false;;
    String lastKey = "";
    String dueKar = "";


    public KeyTouchManager(MiniKeyboard ims){
        this.ims = ims;
    }
    public void init(){
        keyboardContainer = ims.keyboardContainer;
        physicalKeyboard = ims.physicalKeyboard;
        popupWindow = ims.popupWindow;
        popupView = ims.popupView;
        ks = ims.keyeventSender;
        createDrawables(ims.getResources());
        Thread thread = new Thread(this::setTouchListeners);
        thread.start();
    }

    private void createDrawables(Resources res){
        touchIndicators[0] = res.getDrawable(R.drawable.normal_key);
        touchIndicators[1] = res.getDrawable(R.drawable.ondown_key);
        touchIndicators[2] = res.getDrawable(R.drawable.onlongpress_key);

        //borders[2] = res.getDrawable(R.drawable.onlongpress_key);

    }




    public void setInputConnection(InputConnection inputConnection){
        ks.ic = inputConnection;
    }


    public void setTouchListeners() {
        setTabTouchListener();
        setSecondaryTouchListener("2", "@", "২", KeyEvent.KEYCODE_F2);
        setSecondaryTouchListener("3", "#", "৩", KeyEvent.KEYCODE_F3);
        setSecondaryTouchListener("4", "$", "৪", "৳", KeyEvent.KEYCODE_F4);
        setSecondaryTouchListener("5", "%", "৫", KeyEvent.KEYCODE_F5);
        setSecondaryTouchListener("6", "^", "৬", KeyEvent.KEYCODE_F6);
        setSecondaryTouchListener("7", "&", "৭", "ঁ", KeyEvent.KEYCODE_F7);
        setKeyTouchListener("8", "*", "৮", "-", "_");
        setKeyTouchListener("9", "(", "৯", "=", "+");
        setKeyTouchListener("0", ")", "০", "\\", "|");


        setEscTouchListener();
        setPrimaryTouchListener("w", "W", "য", "য়", KeyEvent.KEYCODE_W);
        setPrimaryTouchListener("e", "E", "ড", "ঢ", KeyEvent.KEYCODE_E);
        setPrimaryTouchListener("r", "R", "প", "ফ", KeyEvent.KEYCODE_R);
        setPrimaryTouchListener("t", "T", "ট", "ঠ",KeyEvent.KEYCODE_T);
        setPrimaryTouchListener("y", "Y", "চ", "ছ",KeyEvent.KEYCODE_Y);
        setPrimaryTouchListener("u", "U", "জ", "ঝ",KeyEvent.KEYCODE_U);
        setPrimaryTouchListener("i", "I", "হ", "ঞ", KeyEvent.KEYCODE_I);
        setKeyTouchListener("o", "O", "গ", "ঘ", "[", "{", KeyEvent.KEYCODE_O, KeyEvent.KEYCODE_LEFT_BRACKET);
        setKeyTouchListener("p", "P", "ড়", "ঢ়", "]", "}", KeyEvent.KEYCODE_P, KeyEvent.KEYCODE_RIGHT_BRACKET);


        setProblemTouchListener("a", "A", "ৃ", "\u200D", KeyEvent.KEYCODE_A);
        setProblemTouchListener("s", "S", "ু", "ূ", KeyEvent.KEYCODE_S);
        setProblemTouchListener("d", "D", "ি", "ী", KeyEvent.KEYCODE_D);
        setProblemTouchListener("f", "F", "া", "অ", KeyEvent.KEYCODE_F);
        setProblemTouchListener("g", "G", "্", "।", KeyEvent.KEYCODE_G);
        setPrimaryTouchListener("h", "H", "ব", "ভ", KeyEvent.KEYCODE_H);
        setPrimaryTouchListener("j", "J", "ক", "খ", KeyEvent.KEYCODE_J);
        setKeyTouchListener("k", "K", "ত", "থ", ";", ":", KeyEvent.KEYCODE_K, KeyEvent.KEYCODE_SEMICOLON);
        setKeyTouchListener("l", "L", "দ", "ধ", "'", "\"", KeyEvent.KEYCODE_L, KeyEvent.KEYCODE_APOSTROPHE);
        setBkspTouchListener();


        setKeyTouchListener(",", "<", ",", "<", "`", "~", KeyEvent.KEYCODE_COMMA, -1);
        setPrimaryTouchListener("z", "Z", "্র", "\u200D্য", KeyEvent.KEYCODE_Z);
        setProblemTouchListener("x", "X", "ও", "ৗ", KeyEvent.KEYCODE_X);
        setProblemTouchListener("c", "C", "ে", "ৈ", KeyEvent.KEYCODE_C);
        setPrimaryTouchListener("v", "V", "র", "ল", KeyEvent.KEYCODE_V);
        setPrimaryTouchListener("b", "B", "ন", "ণ", KeyEvent.KEYCODE_B);
        setPrimaryTouchListener("n", "N", "স", "ষ", KeyEvent.KEYCODE_N);
        setKeyTouchListener("m", "M", "ম", "শ", "/", "?", -1, KeyEvent.KEYCODE_SLASH);
        setPrimaryTouchListener("up", KeyEvent.KEYCODE_DPAD_UP);
        setEnterTouchListener();


        setKeyTouchListener(".", ">", ".", ">", "ৎ", "ঃ", KeyEvent.KEYCODE_PERIOD, -1);
        setPrimaryTouchListener("home", KeyEvent.KEYCODE_MOVE_HOME);
        setPrimaryTouchListener("end", KeyEvent.KEYCODE_MOVE_END);
        setSpaceTouchListener();
        setClipboardTouchListener();
        setPrimaryTouchListener("left", KeyEvent.KEYCODE_DPAD_LEFT);
        setPrimaryTouchListener("down", KeyEvent.KEYCODE_DPAD_DOWN);
        setPrimaryTouchListener("right", KeyEvent.KEYCODE_DPAD_RIGHT);

    }


    private void setProblemTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, int primaryKeyCode){
        boolean shouldIgnoreAltC = primaryKeyCode == 31;

        View view = keyboardContainer.findViewWithTag(primaryKey);
        view.setOnTouchListener(new GestureListener(){
            public void onActionDown() {
                indicateOnKeyDown(view);
            }

            public void onClick() {
                if (isEnglish) ks.sendText(primaryKey);
                else {
                    if ("্".equals(banglaKey)) {
                        ks.sendText(banglaKey);
                        switch (lastKey){
                            case "ি": case "ে": case "ৈ":
                                dueKar = lastKey;
                        }
                        lastKey = banglaKey;
                        return;
                    }
                    boolean shouldSendKey = true;
                    boolean isHosonto = "্".equals(lastKey);
                    dueKar = "";
                    switch (banglaKey){
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
                            }
                            else dueKar = banglaKey;
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
                            }
                            else dueKar = banglaKey;
                            break;
                    }
                    if (shouldSendKey) {
                        ks.sendText(banglaKey);
                        lastKey = banglaKey;
                    }
                }
            }
            public void onSwipeUp() {
                if (isEnglish) ks.sendText(shiftXPrimary);
                else {
                    boolean shouldSendKey = true;
                    boolean isHosonto = "্".equals(lastKey);
                    dueKar = "";

                    switch (shiftXBangla){
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
                            }
                            else dueKar = shiftXBangla;
                            break;
                    }
                    if (shouldSendKey) {
                        ks.sendText(shiftXBangla);
                        lastKey = shiftXBangla;
                    }
                }
            }
            public void onSwipeLeft() {
                ks.sendCtrlXShiftXKey(primaryKeyCode);

            }
            public void onSwipeRight() {
                if (shouldIgnoreAltC) return;
                ks.sendAltXKey(primaryKeyCode);

            }
            public void onSwipeDown() {
                ks.sendCtrlXKey(primaryKeyCode);

            }
            public void onActionUp() {
                indicateOnKeyUp(view);
            }
        });
    }




    /*private void setMouseController() {
        View key = keyboardView.findViewWithTag("pointer");
        key.setOnTouchListener(new TrackpadListner(){
            public void onClick() {
                //Left Click
            }
            public void onMove(int x, int y) {
                //Move pointer
            }
        });
        key = keyboardView.findViewWithTag("wheel");
        key.setOnTouchListener(new GestureListener(){
            public void onClick() {
                //Right Click
            }
            public void onSwipeUp() {
                //Scroll Up
            }
            public void onSwipeLeft() {
                //Scroll Left
            }
            public void onSwipeRight() {
                //Scroll Right
            }
            public void onSwipeDown() {
                //Scroll Down
            }
        });
        key = keyboardView.findViewWithTag("mode");
        key.setOnTouchListener(new GestureListener(){
            public void onActionUp() {}
            public void onClick() {}
            public void onSwipeUp() {}
            public void onSwipeLeft() {}
            public void onSwipeRight() {}
            public void onSwipeDown() {}
        });
    }*/
    private void setTabTouchListener() {
        View view = keyboardContainer.findViewWithTag("1");
        view.setOnTouchListener(new GestureListener(true){
            final int tab = KeyEvent.KEYCODE_TAB;
            public void onActionDown() {
                indicateOnKeyDown(view);
                if (!isEnglish) lastKey = dueKar = "";
            }

            public void onClick() {
                if (isEnglish) ks.sendText("1");
                else ks.sendText("১");
            }
            public void onSwipeUp() {
                ks.sendText("!");
            }
            public void onSwipeRight() {
				ks.sendKey(tab);
            }
            public void onSwipeDown() {
                ks.sendCtrlXKey(tab);
            }
            public void onLongPress() {
                indicateOnLongPress(view);
            }
            public void onLongClick() {
                ks.sendKey(tab);
            }
            public void onLongSwipeUp() {
                ks.sendShiftXKey(tab);
            }
            public void onLongSwipeRight() {
                ks.sendAltXKey(tab);
            }
            public void onLongSwipeDown() {
                //sendCtrlXKey(secondaryKey);
            }

            public void onActionUp() {
                indicateOnKeyUp(view);
            }

        });

    }
    private void setEscTouchListener() {
        boolean isBasisKeyboard = false;
        View view = keyboardContainer.findViewWithTag("q");
        view.setOnTouchListener(new GestureListener(){
            public void onActionDown() {
                indicateOnKeyDown(view);
                if (!isEnglish) lastKey = dueKar = "";
            }
            public void onClick() {
                if (isEnglish) ks.sendText("q");
                else {
                    ks.sendText("ঙ");
                    lastKey = "ঙ";
                }
            }
            public void onSwipeUp() {
                if (isEnglish) ks.sendText("Q");
                else {
                    ks.sendText("ং");
                    lastKey = "ং";
                }
            }
            public void onSwipeRight() {
                ks.sendKey(KeyEvent.KEYCODE_ESCAPE);
            }
            public void onSwipeDown() {
                if (isBasisKeyboard) ks.sendText("q");
                else ks.sendCtrlXKey(KeyEvent.KEYCODE_Q);
            }
            public void onActionUp() {
                indicateOnKeyUp(view);
            }

        });

    }
    private void setBkspTouchListener() {
        View key = keyboardContainer.findViewWithTag("bksp");
        key.setOnTouchListener(new GestureListener(){
            public void onActionDown(){
                if (!isEnglish) lastKey = dueKar = "";
            }

            public void onClick()  {
                ks.sendKey(KeyEvent.KEYCODE_DEL);
            }
            public void onSwipeUp()  {
                ks.sendKey(KeyEvent.KEYCODE_FORWARD_DEL);}
            public void onSwipeLeft()  {
                ks.sendCtrlXKey(KeyEvent.KEYCODE_FORWARD_DEL);
            }
            public void onSwipeDown()  {
                ks.sendCtrlXKey(KeyEvent.KEYCODE_DEL);

            }
        });

    }
    private void setEnterTouchListener() {
        View key = keyboardContainer.findViewWithTag("enter");
        key.setOnTouchListener(new GestureListener(){
            public void onClick() {
                ks.sendChar((char) 10);
            }
            //public void onSwipeTop()  {ks.sendCtrlXKey(KeyEvent.KEYCODE_FORWARD_DEL);}
            //public void onSwipeLeft() {/*sendKey(KeyEvent.KEYCODE_FORWARD_DEL);*/}
            //public void onSwipeBottom()  {ks.sendCtrlXKey(KeyEvent.KEYCODE_DEL);}

        });

    }
    private void setSpaceTouchListener() {

        TextView view = keyboardContainer.findViewWithTag("space");
        view.setOnTouchListener(new GestureListener(){
            public void onActionDown() {
                setBackground(view, 1);
                if (!isEnglish) lastKey = dueKar = "";
            }
            public void onActionUp() {
                if(isShiftHoldDown) indicateOnLongPress(view);
                else setBackground(view, 0);
            }
            public void onClick() {
                //if (lastKey == "্") ks.sendText("্");
                ks.sendKey(KeyEvent.KEYCODE_SPACE);
            }

            public void onSwipeUp() {
                isShiftHoldDown = !isShiftHoldDown;
                if(isShiftHoldDown) ks.sendKeyDown(ks.modShift);
                else ks.sendKeyUp(ks.modShift);
            }
            //public void onSwipeTop()  {ks.sendCtrlXKey(KeyEvent.KEYCODE_FORWARD_DEL);}
            //public void onSwipeLeft() {/*sendKey(KeyEvent.KEYCODE_FORWARD_DEL);*/}
            public void onSwipeDown() {
                isEnglish = !isEnglish;
                view.setText(isEnglish ? "English" : "বাংলা");
            }


        });

    }
    private void setClipboardTouchListener() {
        View key = keyboardContainer.findViewWithTag("clipboard");
        key.setOnTouchListener(new GestureListener(){
            public void onActionDown(){
                if (!isEnglish) lastKey = dueKar = "";
            }
            public void onClick() {ks.sendCtrlXKey(KeyEvent.KEYCODE_V);}
            //public void onSwipeUp()  {ks.sendShiftXKey(KeyEvent.KEYCODE_INSERT);}
            public void onSwipeLeft() {ks.sendCtrlXKey(KeyEvent.KEYCODE_INSERT);}
            public void onSwipeRight() {ks.sendShiftXKey(KeyEvent.KEYCODE_INSERT);}
            public void onSwipeDown() {ks.sendCtrlXKey(KeyEvent.KEYCODE_C);}
        });

    }


    private void setPrimaryTouchListener(String tag, int primaryKeyCode) {
        View view = keyboardContainer.findViewWithTag(tag);
        view.setOnTouchListener(new GestureListener() {
            public void onActionDown() {
                setBackground(view, 1);
                if (!isEnglish) lastKey = dueKar = "";
            }

            public void onClick() {
                ks.sendKey(primaryKeyCode);
            }
            public void onSwipeUp() {
                ks.sendShiftXKey(primaryKeyCode);
            }
            public void onSwipeLeft() {
                ks.sendCtrlXShiftXKey(primaryKeyCode);
            }
            public void onSwipeRight() {
                ks.sendAltXKey(primaryKeyCode);
            }
            public void onSwipeDown() {
                ks.sendCtrlXKey(primaryKeyCode);
            }
            public void onActionUp() {
                setBackground(view, 0);
            }
        });
    }
    private void setPrimaryTouchListener(String primaryKey, String shiftXPrimary){
        setPrimaryTouchListener(primaryKey, shiftXPrimary, -1);
    }
    private void setPrimaryTouchListener(String primaryKey, String shiftXPrimary, int primaryKeyCode){
        setKeyTouchListener(primaryKey, shiftXPrimary, null, null, null, null, primaryKeyCode, -1);
    }
    private void setPrimaryTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, int primaryKeyCode){
        setKeyTouchListener(primaryKey, shiftXPrimary, banglaKey, shiftXBangla, null, null, primaryKeyCode, -1);
    }
    private void setSecondaryTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, int secondaryKeyCode){
        setKeyTouchListener(primaryKey, shiftXPrimary, banglaKey, shiftXPrimary, null, null, -1, secondaryKeyCode);
    }
    private void setSecondaryTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, int secondaryKeyCode){
        setKeyTouchListener(primaryKey, shiftXPrimary, banglaKey, shiftXBangla, null, null, -1, secondaryKeyCode);
    }
    private void setKeyTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String secondaryKey, String shiftXSecondary) {
        setKeyTouchListener(primaryKey, shiftXPrimary, banglaKey, shiftXPrimary, secondaryKey, shiftXSecondary, -1, -1);
    }
    private void setKeyTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, String secondaryKey, String shiftXSecondary) {
        setKeyTouchListener(primaryKey, shiftXPrimary, banglaKey, shiftXBangla, secondaryKey, shiftXSecondary, -1, -1);
    }
    private void setKeyTouchListener(String primaryKey, String shiftXPrimary, String banglaKey, String shiftXBangla, String secondaryKey, String shiftXSecondary, int primaryKeyCode, int secondaryKeyCode){
        boolean isPrimaryKeyCode = (primaryKeyCode != -1);
        //boolean isShiftXPrimaryKeyCode = isPrimaryKeyCode && (shiftXPrimary == null);

        boolean isSecondaryKey = (secondaryKey != null);
        boolean isSecondaryKeyCode = (secondaryKeyCode != -1);
        boolean isLongClickable = isSecondaryKey || isSecondaryKeyCode;
        boolean isShiftXSecondaryKeyCode = isSecondaryKeyCode && !isSecondaryKey;

        View view = keyboardContainer.findViewWithTag(primaryKey);
        view.setOnTouchListener(new GestureListener(isLongClickable){
            public void onActionDown() {
                indicateOnKeyDown(view);
            }

            public void onClick() {
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
            public void onSwipeUp() {
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
            public void onSwipeLeft() {
                if (isPrimaryKeyCode) {
                    ks.sendCtrlXShiftXKey(primaryKeyCode);
                }
                else ks.sendText(primaryKey);
            }
            public void onSwipeRight() {
                if (isPrimaryKeyCode) {
                    ks.sendAltXKey(primaryKeyCode);
                }
                else ks.sendText(primaryKey);
            }
            public void onSwipeDown() {
                if (isPrimaryKeyCode) {
                    ks.sendCtrlXKey(primaryKeyCode);
                }
                else ks.sendText(primaryKey);
            }
            public void onLongPress() {
                indicateOnLongPress(view);
            }
            public void onLongClick() {
                if (isShiftXSecondaryKeyCode) {
                    ks.sendKey(secondaryKeyCode);
                }
                else ks.sendText(secondaryKey);
            }
            public void onLongSwipeUp() {
                if (isShiftXSecondaryKeyCode) {
                    ks.sendShiftXKey(secondaryKeyCode);
                }
                else ks.sendText(shiftXSecondary);
            }
            public void onLongSwipeLeft() {
                if (isSecondaryKeyCode) {
                    ks.sendCtrlXShiftXKey(secondaryKeyCode);
                }
            }
            public void onLongSwipeRight() {
                if (isSecondaryKeyCode) {
                    ks.sendAltXKey(secondaryKeyCode);
                }
            }
            public void onLongSwipeDown() {
                if (isSecondaryKeyCode) {
                    ks.sendCtrlXKey(secondaryKeyCode);
                }
            }
            public void onActionUp() {
                indicateOnKeyUp(view);
            }
        });
    }



    private void show(View v){
        v.setVisibility(View.VISIBLE);
    }
    private void hide(View v){
        v.setVisibility(View.GONE);
    }

    private void indicateOnKeyDown(View v){
        popupView.setBackgroundColor(0xffcccccc);
        popupWindow.showAsDropDown(v, 0, -popupOffset);
        setBackground(v, 1);
    }
    private void indicateOnLongPress(View v){
        popupView.setBackgroundColor(0xffff9800);
        setBackground(v, 2);
    }
    private void indicateOnKeyUp(View v){
        new Handler().postDelayed(()-> {
            popupWindow.dismiss();
            setBackground(v, 0);
        }, 20);
    }
    private void setBackground(View v, int state){
        v.setBackground(touchIndicators[state]);
    }







}




















