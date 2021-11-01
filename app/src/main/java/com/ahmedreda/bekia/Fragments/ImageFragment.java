package com.ahmedreda.bekia.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedreda.bekia.MainActivity;
import com.ahmedreda.bekia.Products_Activity;
import com.ahmedreda.bekia.R;
import com.ahmedreda.bekia.UserData;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ImageFragment extends Fragment {

    private static final int RESULT_SELECT_IMG = 1 ;

    public ImageFragment() {
        // Required empty public constructor
    }


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Uri resultUri = null   ;
    private CircleImageView profileimage ;
    private Button Skip ;
    private String UserID = "" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        profileimage = view.findViewById(R.id.profile_image);
        Skip = view.findViewById(R.id.skip);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SelectImage();
            }
        });

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return  view ;
    }

    public void UploadProfiileImagetoStorage(String userid , Uri resuluri ){
        storageReference.child("ProfileImage").child(userid+".jpg").putFile(resuluri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == RESULT_SELECT_IMG && resultCode == Activity.RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();

                CropImage.activity(selectedImage)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(800, 800)
                        .setMaxCropResultSize(1000, 1000)
                        .start(getActivity());
            }
            // when image is cropped
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    resultUri = result.getUri();
                    Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    profileimage.setImageBitmap(bitmap);
                    UserID = firebaseAuth.getCurrentUser().getUid().toString();
                    UploadProfiileImagetoStorage(UserID,resultUri);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                    },4000);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong"+e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }



    public void SelectImage(){
        CropImage.activity()
                .start(getContext(), this);
    }








}