package com.example.mmt5544.bookappointment.ui;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.example.mmt5544.bookappointment.BR;
import com.example.mmt5544.bookappointment.R;
import com.example.mmt5544.bookappointment.databinding.FragmentFirstScreenBinding;
import com.example.mmt5544.bookappointment.util.AppConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class FirstScreenFragment extends BaseFragment implements Observer<String>,
        AdapterView.OnItemSelectedListener, LocationListener {

    public static final String TAG = "FirstScreenFragment";
    private LocationManager locationManager;

    String[] testsName = {"MRI", "MRIA", "MRIAB", "MRIABC", "MRIABCD", "MRIABCDE", "MRIABCDEF", "MRIABCDEFG"};
    String[] cityName = {"MUMBAI", "PUNE", "PUNJAB", "MURADABAD", "DELHI", "DEHRADUN", "LUCKNOW", "LUDHIANA"};
    // Spinner Drop down elements
    List<String> categories;

    public FirstScreenFragment() {
        // Required empty public constructor
    }

    public static FirstScreenFragment newInstance() {
        return new FirstScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstScreenViewModel.getCurrentEventLiveData().observe(this, this);
        firstScreenViewModel.setHeader(getString(R.string.first_heading));
        firstScreenViewModel.setSubHeading(getString(R.string.subheading));
        firstScreenViewModel.setDate(getString(R.string.date_request));

        Location location = getLastKnownLocation();

        if (location != null) {
            onLocationChanged(location);
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFirstScreenBinding fragmentFirstScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_screen, container, false);
        fragmentFirstScreenBinding.setVariable(BR.viewModel, firstScreenViewModel);
        init();
        return fragmentFirstScreenBinding.getRoot();
    }

    private void init() {
        ArrayAdapter<String> adapterTest = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, testsName);
        firstScreenViewModel.setExamAdapter(adapterTest);
        firstScreenViewModel.setExamThreshold(1);

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, cityName);
        firstScreenViewModel.setCityAdapter(adapterCity);
        firstScreenViewModel.setCityThreshold(1);

        //Time Requested Info
        categories = new ArrayList<>();
        categories.add("10 AM - 11 AM");
        categories.add("11 AM - 12 AM");
        categories.add("12 AM - 13 PM");
        categories.add("13 PM - 14 PM");
        categories.add("16 PM - 17 PM");
        categories.add("17 PM - 18 PM");
        categories.add("18 PM - 19 PM");
        categories.add("19 PM - 20 PM");

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstScreenViewModel.setTimeAdapter(timeAdapter);
    }

    @Override
    public void onLocationChanged(Location location) {
        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            String adminLocation = address.get(0).getAdminArea();
            if (adminLocation != null && !adminLocation.isEmpty()) {
                firstScreenViewModel.setAddress(adminLocation);
            }
        } catch (Exception e) {
            // Nothing to do
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                        firstScreenViewModel.setDate(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void launchSecondScreenFragment() {
        SecondScreenFragment secondScreenFragment = SecondScreenFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("second")
                .replace(R.id.fragment_container, secondScreenFragment, SecondScreenFragment.TAG).commit();
    }

    @Override
    public void onChanged(@Nullable String s) {
        if (AppConstants.VIEW_MODEL_INTERACTION.OPEN_CAMERA.equals(s)) {
            launchCamera(true);
        } else if (AppConstants.VIEW_MODEL_INTERACTION.OPEN_DATE_PICKER.equals(s)) {
            openDatePicker();
        } else if (AppConstants.VIEW_MODEL_INTERACTION.NEXT_SCREEN.equals(s)) {
            launchSecondScreenFragment();
        }
    }
}
