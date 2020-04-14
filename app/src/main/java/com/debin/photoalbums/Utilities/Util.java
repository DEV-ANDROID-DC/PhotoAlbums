package com.debin.photoalbums.Utilities;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static void ToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
