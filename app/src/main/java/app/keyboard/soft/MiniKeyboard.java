package app.keyboard.soft;

import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class MiniKeyboard extends InputMethodService {

    LinearLayout keyboardContainer;
    KeyTouchManager touchManager;
    PhysicalKeyboard physicalKeyboard;
    public KeyeventSender keyeventSender;

    LinearLayout popupLayout;
    PopupWindow popupWindow;
    View popupView;
    
    
    


    @Override
    public View onCreateInputView() {
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.keyboard_mini, null);
        keyboardContainer = layout.findViewById(R.id.keyboard_container);

        init();
        return layout;
    }

    public void init(){
        popupLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_layout, null);
        popupWindow = createPopupWindow(popupLayout);
        popupView = popupLayout.findViewById(R.id.popup_view);


        keyeventSender = new KeyeventSender(this);


        touchManager = new KeyTouchManager(this);
        physicalKeyboard = new PhysicalKeyboard(this);


        touchManager.init();


        setupNavigationBar();
        updateKeyboardHeight();


        physicalKeyboard.init();



    }

    @Override
    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
        super.onStartInputView(editorInfo, restarting);
        touchManager.setInputConnection(getCurrentInputConnection());
        /*boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isFullKeyboard(editorInfo)){
            show(functionRow);
            percentage = landscape ? 50 : 38;
        }
        else {
            hide(functionRow);
            percentage = landscape ? 45 : 32;
        }
        updateKeyboardHeight();*/
        //spaceBar.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


    }

    private void setupNavigationBar(){
        Window window = getWindow().getWindow();
        if (window != null) {
            View decoreView = window.getDecorView();
            decoreView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void updateKeyboardHeight(){
        keyboardContainer.getLayoutParams().height = getHeight();
        keyboardContainer.requestLayout();

    }
    private int getHeight(){
        boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int percentage = landscape ? 42 : 30;
        WindowManager wm =  (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels * percentage / 100;
    }


    private PopupWindow createPopupWindow(LinearLayout popupLayout) {
        return new PopupWindow(popupLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                false);
    }

    private void showInputMethodPicker() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .showInputMethodPicker();
    }
    private boolean isFullKeyboard(EditorInfo ei) {
        String pkg = ei.packageName;
        if (ei == null || pkg == null) return false;
        return ((pkg.equalsIgnoreCase("com.termux.x11")
                || pkg.equalsIgnoreCase("com.pslib.connectbot")
        ));
    }


    /*void toast(String text){
        spaceBar.setText(text);

    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (physicalKeyboard.onKeyDown(keyCode, event)) return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (physicalKeyboard.onKeyUp(keyCode, event)) return true;
        return super.onKeyUp(keyCode, event);
    }
}















