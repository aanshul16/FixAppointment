package com.example.mmt5544.bookappointment.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mmt5544.bookappointment.BR;
import com.example.mmt5544.bookappointment.R;
import com.example.mmt5544.bookappointment.databinding.FragmentSecondScreenBinding;
import com.example.mmt5544.bookappointment.util.AppConstants;

import java.util.Calendar;

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
        firstScreenViewModel.setFirstName(getString(R.string.first_name));
        firstScreenViewModel.setSecondName(getString(R.string.second_name));
        firstScreenViewModel.setBirthDate(getString(R.string.birth_date));
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
    public void onChanged(@Nullable String s) {
        if (AppConstants.VIEW_MODEL_INTERACTION.OPEN_AADHAR_CARD.equals(s)) {
            launchCamera(false);
        } else if (AppConstants.VIEW_MODEL_INTERACTION.BIRTH_DATE_PICKER.equals(s)) {
            openDatePicker();
        }
    }
}
