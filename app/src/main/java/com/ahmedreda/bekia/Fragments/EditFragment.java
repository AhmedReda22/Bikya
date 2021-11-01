package com.ahmedreda.bekia.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedreda.bekia.Products_Activity;
import com.ahmedreda.bekia.R;
import com.ahmedreda.bekia.UserData;
import com.ahmedreda.bekia.ViewModels.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditFragment extends Fragment {

    private static final int RESULT_SELECT_IMG = 2 ;

    public EditFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    UserViewModel userViewModel ;
    private Uri resultUri = null ;
    private String UserID = "" ;
    private EditText name , email, phone ;
    private Button save ;
    private CircleImageView profile_image ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone_number);
        save = view.findViewById(R.id.save);
        profile_image = view.findViewById(R.id.profile_image);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        userViewModel.getUserData();
        userViewModel.userDataMutableLiveData.observe(getActivity(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                email.setText(userData.getEmail());
                name.setText(userData.getName());
                phone.setText(userData.getPhoneNumber());
            }
        });

        UserID = firebaseAuth.getCurrentUser().getUid().toString() ;
        storageReference.child("ProfileImage").child(UserID+".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull  Task<Uri> task) {
                String imageUri = task.getResult().toString();
                Glide.with(getActivity()).asBitmap().load(imageUri).placeholder(R.drawable.user)
                        .into(profile_image);
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID = firebaseAuth.getCurrentUser().getUid().toString();
                firebaseAuth.getCurrentUser().updateEmail(email.getText().toString());
                UpdateData(UserID);
                startActivity(new Intent(getActivity(),Products_Activity.class));
            }
        });



        return view ;
    }


    public void SelectImage(){
        CropImage.activity()
                .start(getContext(), this);
    }

    public void UploadProfiileImagetoStorage(String userid , Uri resuluri ){
        storageReference.child("ProfileImage").child(userid+".jpg").putFile(resuluri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                  //  Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                }else{
                 //   Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateData(String UserId) {
                    String Name = name.getText().toString().trim();
                    String Email = email.getText().toString().trim();
                    String Phone = phone.getText().toString().trim();
                    HashMap<String,Object> update = new HashMap<>();
                    update.put("name",Name);
                    update.put("email",Email);
                    update.put("phoneNumber",Phone);
                    firebaseFirestore.collection("User").document(UserId).update(update);
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
                    profile_image.setImageBitmap(bitmap);
                    UserID = firebaseAuth.getCurrentUser().getUid().toString();
                    UploadProfiileImagetoStorage(UserID,resultUri);
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


}