package com.example.mmt5544.bookappointment.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mmt5544.bookappointment.BR;
import com.example.mmt5544.bookappointment.R;
import com.example.mmt5544.bookappointment.databinding.FragmentSecondScreenBinding;
import com.example.mmt5544.bookappointment.util.AppConstants;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.Calendar;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class SecondScreenFragment extends BaseFragment implements Observer<String> {

    public static final String TAG = "SecondScreenFragment";

    public SecondScreenFragment() {
        // Required empty public constructor
    }

    public static SecondScreenFragment newInstance() {
        return new SecondScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstScreenViewModel.getCurrentEventLiveData().observe(this, this);
        firstScreenViewModel.setHeader(getString(R.string.first_heading));
        firstScreenViewModel.setSecondSubHeading(getString(R.string.secondSubheading));
        firstScreenViewModel.setReqAppointment(getString(R.string.request_apptmnt));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSecondScreenBinding fragmentSecondScreenBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_second_screen, container, false);
        fragmentSecondScreenBinding.setVariable(BR.viewModel, firstScreenViewModel);
        return fragmentSecondScreenBinding.getRoot();
    }

    public void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        firstScreenViewModel.setBirthDate(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        firstScreenViewModel.setFirstName(data.getStringExtra("firstname"));
        firstScreenViewModel.setSecondName(data.getStringExtra("secondname"));
        firstScreenViewModel.setBirthDate(data.getStringExtra("dob"));
    }

    @Override
    public void onChanged(@Nullable String s) {
        if (AppConstants.VIEW_MODEL_INTERACTION.OPEN_AADHAR_CARD.equals(s)) {
            //launchCamera(false);
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivityForResult(intent, 100);
        } else if (AppConstants.VIEW_MODEL_INTERACTION.BIRTH_DATE_PICKER.equals(s)) {
            openDatePicker();
        }
    }
}
