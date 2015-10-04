package com.gmail.kiyoonko.virms;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener

/**
 * Created by JaehyeongLee on 10/3/15.
 */

public void class DrawPoint {
    public OnTouchListener getListener(){
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double x = v.getX()+0.1;
                double y = v.getY()+0.1;
                double z = v.getZ();
                return true;
            }
        }
    }
}
