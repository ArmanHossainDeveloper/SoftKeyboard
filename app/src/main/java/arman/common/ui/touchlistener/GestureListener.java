package arman.common.ui.touchlistener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import arman.common.infocodes.InfoCode;

public class GestureListener implements View.OnTouchListener {

    boolean moved;
    float x1, y1;
    int holdDelay = 250;
    boolean isLongPress;
    boolean isHolding;
    boolean shouldLongPressOnHold;
    public GestureListener(){
        this.shouldLongPressOnHold = false;
    }
    public GestureListener(boolean shouldLongPressOnHold){
        this.shouldLongPressOnHold = shouldLongPressOnHold;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onActionDown();
                x1 = event.getX();
                y1 = event.getY();
                moved = false;
                isLongPress = false;
                isHolding = true;
                if (shouldLongPressOnHold) {
                    new Handler().postDelayed(() -> {
                        if (!moved && isHolding) {
                            onLongPress();
                            isLongPress = true;
                        }
                    }, holdDelay);
                }
                return true;


            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isHolding = false;
                if (!moved) {
                    if (isLongPress) onLongClick();
                    else onClick();
                }
                onActionUp();
                return true;

            case MotionEvent.ACTION_MOVE:
                if(!moved){
                    float x = event.getX() - x1;
                    float y = event.getY() - y1;
                    if (y < -30 ){onMove(1);}
                    else if(y > 30) {onMove(4);}
                    else if (x < -30){onMove(2);}
                    else if (x > 30){onMove(3);}
                }
                return true;
        }
        return false;
    }

    private void onMove(int direction) {
        moved = true;
        switch (direction){
            case 1:
                if (isLongPress) onLongSwipeUp();
                else onSwipeUp();
                break;
            case 2:
                if (isLongPress) onLongSwipeLeft();
                else onSwipeLeft();
                break;
            case 3:
                if (isLongPress) onLongSwipeRight();
                else onSwipeRight();
                break;
            case 4:
                if (isLongPress) onLongSwipeDown();
                else onSwipeDown();
                break;
        }
    }



    public void onActionDown() {}
    public void onClick() {}
    public void onSwipeUp() {}
    public void onSwipeLeft() {}
    public void onSwipeRight() {}
    public void onSwipeDown() {}


    public void onLongPress() {}
    public void onLongClick() {}
    public void onLongSwipeUp() {}
    public void onLongSwipeLeft() {}
    public void onLongSwipeRight() {}
    public void onLongSwipeDown() {}

    public void onActionUp() {}



    void log(String string){
        InfoCode.log(string);
    }

}
















