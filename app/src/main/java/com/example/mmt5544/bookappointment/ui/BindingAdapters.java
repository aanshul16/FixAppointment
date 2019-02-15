package com.example.mmt5544.bookappointment.ui;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingAdapters {
    @BindingAdapter("bindText")
    public static void setBindText(TextView view, String text) {
        view.setText(text != null ? text : "");
    }

    @BindingAdapter(value = {"setAdapter", "setThreshold"}, requireAll = true)
    public static void setAutoTextView(AutoCompleteTextView autoTextView, ArrayAdapter<String> arrayAdapter, int threshold) {
        autoTextView.setAdapter(arrayAdapter);
        autoTextView.setThreshold(threshold);
    }

    @BindingAdapter("setSpinnerAdapter")
    public static void setSpinnerAdapter(AppCompatSpinner spinner, ArrayAdapter<String> spinnerAdapter) {
        spinner.setAdapter(spinnerAdapter);
    }

    @BindingAdapter("bindTextWatcher")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        if (textWatcher != null) {
            editText.addTextChangedListener(textWatcher);
        }
    }

    @BindingAdapter("setImageBitmap")
    public static void bindImageBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
