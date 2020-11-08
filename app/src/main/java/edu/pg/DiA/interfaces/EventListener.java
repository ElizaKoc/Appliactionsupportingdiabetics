package edu.pg.DiA.interfaces;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface EventListener {

    void onEvent(@Nullable Fragment fragment);
}
