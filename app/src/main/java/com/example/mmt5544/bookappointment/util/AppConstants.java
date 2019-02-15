package com.example.mmt5544.bookappointment.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppConstants {
    @StringDef({VIEW_MODEL_INTERACTION.OPEN_DATE_PICKER, VIEW_MODEL_INTERACTION.OPEN_CAMERA, VIEW_MODEL_INTERACTION.NEXT_SCREEN,
            VIEW_MODEL_INTERACTION.BIRTH_DATE_PICKER, VIEW_MODEL_INTERACTION.OPEN_AADHAR_CARD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VIEW_MODEL_INTERACTION {
        String OPEN_DATE_PICKER = "OPEN_DATE_PICKER";
        String OPEN_CAMERA = "OPEN_CAMERA";
        String NEXT_SCREEN = "NEXT_SCREEN";
        String BIRTH_DATE_PICKER = "BIRTH_DATE_PICKER";
        String OPEN_AADHAR_CARD = "OPEN_AADHAR_CARD";
    }
}
