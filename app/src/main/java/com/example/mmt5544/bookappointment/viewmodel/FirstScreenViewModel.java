package com.example.mmt5544.bookappointment.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.mmt5544.bookappointment.R;
import com.example.mmt5544.bookappointment.util.AppConstants;

public class FirstScreenViewModel extends ViewModel {
    private String header;
    private String subHeading;
    private String address;
    private ArrayAdapter<String> examAdapter;
    private ArrayAdapter<String> cityAdapter;
    private int examThreshold;
    private int cityThreshold;
    private ArrayAdapter<String> timeAdapter;
    private ObservableField<String> date = new ObservableField<>("");
    private String noteSection;
    private ObservableField<Bitmap> imageSelected = new ObservableField<>();
    private MutableLiveData<String> currentEventLiveData = new MutableLiveData<>();
    private String secondSubHeading;
    private ObservableField<String> firstName = new ObservableField<>("");
    private ObservableField<String> secondName = new ObservableField<>("");
    private String reqAppointment;
    private ObservableField<String> birthDate = new ObservableField<>("");
    private ObservableField<Bitmap> imageSelectedPersonal = new ObservableField<>();

    public ObservableField<Bitmap> getImageSelectedPersonal() {
        return imageSelectedPersonal;
    }

    public void setImageSelectedPersonal(Bitmap imageSelectedPersonal) {
        this.imageSelectedPersonal.set(imageSelectedPersonal);
    }

    public ObservableField<String> getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getReqAppointment() {
        return reqAppointment;
    }

    public void setReqAppointment(String reqAppointment) {
        this.reqAppointment = reqAppointment;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public ObservableField<String> getFirstName() {
        return firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public ObservableField<String> getSecondName() {
        return secondName;
    }

    public String getSecondSubHeading() {
        return secondSubHeading;
    }

    public void setSecondSubHeading(String secondSubHeading) {
        this.secondSubHeading = secondSubHeading;
    }

    public ObservableField<Bitmap> getImageSelected() {
        return imageSelected;
    }

    public void setImageSelected(Bitmap imageSelected) {
        this.imageSelected.set(imageSelected);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ArrayAdapter<String> getTimeAdapter() {
        return timeAdapter;
    }

    public void setTimeAdapter(ArrayAdapter<String> timeAdapter) {
        this.timeAdapter = timeAdapter;
    }

    public ArrayAdapter<String> getCityAdapter() {
        return cityAdapter;
    }

    public void setCityAdapter(ArrayAdapter<String> cityAdapter) {
        this.cityAdapter = cityAdapter;
    }

    public int getCityThreshold() {
        return cityThreshold;
    }

    public void setCityThreshold(int cityThreshold) {
        this.cityThreshold = cityThreshold;
    }

    public ArrayAdapter<String> getExamAdapter() {
        return examAdapter;
    }

    public int getExamThreshold() {
        return examThreshold;
    }

    public void setExamAdapter(ArrayAdapter<String> examAdapter) {
        this.examAdapter = examAdapter;
    }

    public void setExamThreshold(int examThreshold) {
        this.examThreshold = examThreshold;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.perosnal_camera_icon:
                this.currentEventLiveData.setValue(AppConstants.VIEW_MODEL_INTERACTION.OPEN_AADHAR_CARD);
                break;
            case R.id.birth_date_icon:
                this.currentEventLiveData.setValue(AppConstants.VIEW_MODEL_INTERACTION.BIRTH_DATE_PICKER);
                break;
            case R.id.btn_date_icon:
                this.currentEventLiveData.setValue(AppConstants.VIEW_MODEL_INTERACTION.OPEN_DATE_PICKER);
                break;
            case R.id.camera_icon:
                this.currentEventLiveData.setValue(AppConstants.VIEW_MODEL_INTERACTION.OPEN_CAMERA);
                break;
            case R.id.next_screen:
                this.currentEventLiveData.setValue(AppConstants.VIEW_MODEL_INTERACTION.NEXT_SCREEN);
                break;
            case R.id.req_appointment:
                // Submit Form API HIT
                break;
            default:
                break;
        }
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                noteSection = s.toString();
            } else {
                noteSection = "";
            }
        }
    };

    public LiveData<String> getCurrentEventLiveData() {
        return currentEventLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
