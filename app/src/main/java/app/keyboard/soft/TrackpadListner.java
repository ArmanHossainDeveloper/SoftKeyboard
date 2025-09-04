package app.keyboard.soft;

import android.view.MotionEvent;
import android.view.View;

public class TrackpadListner implements View.OnTouchListener {

    boolean isClickable;
    float x1, y1;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                isClickable = true;
                return true;


            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isClickable) {onClick();}
                return true;

            case MotionEvent.ACTION_MOVE:
                float x2 = event.getX();
                float y2 = event.getY();
                if(isClickable){
                    float x = x2 - x1;
                    float y = y2 - y1;
                    if (Math.abs(x) > 10 || Math.abs(y) > 10){
                        isClickable = false;
                    }
                }
                else onMove((int) x2, (int) y2);
                return true;
        }

        return false;
    }



    public void onClick() {
    }

    public void onMove(int x, int y) {
    }

}



















