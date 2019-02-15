package com.example.mmt5544.bookappointment.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.mmt5544.bookappointment.R;
import com.example.mmt5544.bookappointment.util.Utility;
import com.example.mmt5544.bookappointment.viewmodel.FirstScreenViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class BaseFragment extends Fragment {

    private String userChoosenTask;
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_CAMERA = 2;
    private boolean isFirstFragment;
    protected FirstScreenViewModel firstScreenViewModel;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstScreenViewModel = ViewModelProviders.of(this).get(FirstScreenViewModel.class);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                if (isFirstFragment) {
                    firstScreenViewModel.setImageSelected(bitmap);
                } else {
                    firstScreenViewModel.setImageSelectedPersonal(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isFirstFragment) {
            firstScreenViewModel.setImageSelected(thumbnail);
        } else {
            firstScreenViewModel.setImageSelectedPersonal(thumbnail);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(getString(R.string.take_photo)))
                        cameraIntent();
                    else if (userChoosenTask.equals(getString(R.string.choose_photo)))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    public void launchCamera(boolean isFirstFragment) {
        this.isFirstFragment = isFirstFragment;
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_photo), getString(R.string.cancel_dialog)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals(getString(R.string.take_photo))) {
                    userChoosenTask = getString(R.string.take_photo);
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(getString(R.string.choose_photo))) {
                    userChoosenTask = getString(R.string.choose_photo);
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(getString(R.string.cancel_dialog))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
    }
}
