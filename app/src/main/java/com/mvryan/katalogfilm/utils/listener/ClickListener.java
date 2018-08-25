package com.mvryan.katalogfilm.utils.listener;

import android.view.View;

/**
 * Created by mvryan on 21/08/18.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
