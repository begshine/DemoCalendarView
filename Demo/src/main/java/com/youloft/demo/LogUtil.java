package com.youloft.demo;

import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by javen on 14-4-10.
 */
public class LogUtil {

    public static String logTouchEventAction(MotionEvent event) {
        final int action = event.getActionMasked();
        Field[] fields = MotionEvent.class.getFields();
        for (Field field : fields) {
            if (field.getType() == int.class) {
                try {
                    int value = field.getInt(event);
                    if (action == value) {
                        return field.getName();

                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return "";
    }
}
