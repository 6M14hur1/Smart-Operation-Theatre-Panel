package com.hanan.music.activities;

import android.support.annotation.Nullable;

import com.afollestad.appthemeengine.ATEActivity;
import com.hanan.music.utils.Helpers;

/**
 * Created by naman on 31/12/15.
 */
public class BaseThemedActivity extends ATEActivity {

    @Nullable
    @Override
    public String getATEKey() {
        return Helpers.getATEKey(this);
    }
}
