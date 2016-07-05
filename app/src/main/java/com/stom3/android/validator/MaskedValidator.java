package com.stom3.android.validator;

import android.text.InputFilter;
import android.text.TextWatcher;

public interface MaskedValidator extends TextWatcher, InputFilter {
    String getValue();

    boolean isValid();

    boolean hasFullLength();
}
