package com.pankaj.imageclickandpick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pankaj.imageclickandpick.image_pic_utils.ClickAndPick;
import com.pankaj.imageclickandpick.image_pic_utils.DefaultCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String PHOTOS_KEY = "clickandpick_photos_list";

    protected RecyclerView recyclerView;

    protected View galleryButton;

    private ImagesAdapter imagesAdapter;

    private ArrayList<File> photos = new ArrayList<>();
    private AppPermissions mRuntimePermission;

    //Permission
    private static final String[] CAMERA_ALL_PERMISSION = {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int ALL_PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRuntimePermission= new AppPermissions(this);

        recyclerView = findViewById(R.id.recycler_view);
        galleryButton = findViewById(R.id.gallery_button);

        if (savedInstanceState != null) {
            photos = (ArrayList<File>) savedInstanceState.getSerializable(PHOTOS_KEY);
        }

        imagesAdapter = new ImagesAdapter(this, photos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagesAdapter);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (!mRuntimePermission.hasPermission(CAMERA_ALL_PERMISSION)) {
            mRuntimePermission.requestPermission(MainActivity.this, CAMERA_ALL_PERMISSION, ALL_PERMISSION_REQUEST_CODE);

        }

        ClickAndPick.configuration(this)
                .setImagesFolderName("ClickAndPick sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(true);

        checkGalleryAppAvailability();


        findViewById(R.id.gallery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Some devices such as Samsungs which have their own gallery app require write permission. Testing is advised! */
                ClickAndPick.openGallery(MainActivity.this, 0);
            }
        });


        findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickAndPick.openCamera(MainActivity.this, 0);
            }
        });

        findViewById(R.id.documents_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Some devices such as Samsungs which have their own gallery app require write permission. Testing is advised! */

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    ClickAndPick.openDocuments(MainActivity.this, 0);
                } else {
                    mRuntimePermission.requestPermission(MainActivity.this, CAMERA_ALL_PERMISSION, ALL_PERMISSION_REQUEST_CODE);

                }
            }
        });

        findViewById(R.id.chooser_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickAndPick.openChooserWithDocuments(MainActivity.this, "Pick source", 0);
            }
        });


        findViewById(R.id.chooser_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickAndPick.openChooserWithGallery(MainActivity.this, "Pick source", 0);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    private void checkGalleryAppAvailability() {
        if (!ClickAndPick.canDeviceHandleGallery(this)) {
            //Device has no app that handles gallery intent
            galleryButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSION_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    Toast.makeText(this, "Please Allow All Permissions", Toast.LENGTH_SHORT).show();
                } else {
                    //openCameraDialog();
                }
                break;

        }    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClickAndPick.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, ClickAndPick.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, ClickAndPick.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(ClickAndPick.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == ClickAndPick.ImageSource.CAMERA) {
                    File photoFile = ClickAndPick.lastlyTakenButCanceledPhoto(MainActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        photos.addAll(returnedPhotos);
        imagesAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() - 1);
    }

    @Override
    protected void onDestroy() {
        // Clear any configuration that was done!
        ClickAndPick.clearConfiguration(this);
        super.onDestroy();
    }
}
