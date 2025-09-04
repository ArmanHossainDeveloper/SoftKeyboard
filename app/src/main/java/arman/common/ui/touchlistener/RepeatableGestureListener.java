package arman.common.ui.touchlistener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import arman.common.infocodes.InfoCode;

public class RepeatableGestureListener implements View.OnTouchListener {

    boolean moved;
    float x1, y1;
    boolean shouldRepeatOnHold = false;
    boolean isRepeating = false;
    boolean isHolding = false;
    Handler clickHandler = new Handler();
    Handler combnationHandler = new Handler();
    int holdDelay = 400;
    public RepeatableGestureListener(){
        this.shouldRepeatOnHold = false;
    }
    public RepeatableGestureListener(boolean shouldRepeatOnHold){
        this.shouldRepeatOnHold = shouldRepeatOnHold;
    }


    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                moved = false;
                isRepeating = false;
                isHolding = false;
                if (shouldRepeatOnHold){
                    clickHandler.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            if (!moved) {onClick();}
                            else return;
                            isRepeating = true;
                            clickHandler.postDelayed(this, 100);
                        }
                    }, holdDelay);
                }

                return true;


            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isHolding = false;
                if (!moved && !isRepeating) {onClick();}
                else onActionUp();
                moved = true;

                return true;

            case MotionEvent.ACTION_MOVE:
                if(!moved && !isRepeating){
                    float x = event.getX() - x1;
                    float y = event.getY() - y1;
                    if (y < -20 ){onHold(this::onSwipeUp);}
                    else if(y > 20) {onHold(this::onSwipeDown);}
                    else if (x < -20){onHold(this::onSwipeLeft);}
                    else if (x > 20){onHold(this::onSwipeRight);}

                }
                return true;
        }
        return false;
    }


    private void onHold(Runnable candidate) {
        moved = true;
        isHolding = true;
        candidate.run();
        if (shouldRepeatOnHold){
            combnationHandler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    if (isHolding)  {candidate.run();}
                    else return;
                    isRepeating = true;
                    combnationHandler.postDelayed(this, 300);
                }
            }, holdDelay);
        }
    }

    public void onRepeat() {
    }

    public void onActionUp() {
    }

    public void onClick() {
        //this is normal click
    }

    public void onSwipeUp() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSwipeDown() {
    }

    void log(String string){
        InfoCode.log(string);
    }

}
