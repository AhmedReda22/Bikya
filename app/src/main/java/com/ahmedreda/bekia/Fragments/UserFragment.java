package com.ahmedreda.bekia.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedreda.bekia.MainActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment  {

    public UserFragment() {
        // Required empty public constructor
    }

    private TextView email , name , phone ;
    private CircleImageView profile_image ;
    private Button update , logout , orders , location ;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    UserViewModel userViewModel ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

         email = view.findViewById(R.id.email);
         name = view.findViewById(R.id.name);
         phone = view.findViewById(R.id.phone_number);
         profile_image = view.findViewById(R.id.profile_image);
         update = view.findViewById(R.id.update);
         logout = view.findViewById(R.id.logout);

         update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               EditFragment editFragment = new EditFragment();
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.fragment_container,editFragment).addToBackStack(null)
                       .commit();
             }
         });

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 firebaseAuth.signOut();
                 if (firebaseAuth.getCurrentUser() == null ){
                     Intent intent = new Intent(getActivity(), MainActivity.class);
                     startActivity(intent);
                 }
             }
         });

        String UID = firebaseAuth.getCurrentUser().getUid().toString();
        storageReference.child("ProfileImage").child(UID+".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull  Task<Uri> task) {
                String imageUri = task.getResult().toString();
                Glide.with(getActivity()).asBitmap().load(imageUri).placeholder(R.drawable.user)
                        .into(profile_image);
            }
        });



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


         return view ;




    }





}