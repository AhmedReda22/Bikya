package com.ahmedreda.bekia.ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmedreda.bekia.R;
import com.ahmedreda.bekia.UserData;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public
class UserViewModel extends ViewModel {


    public Context context ;
    public final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public MutableLiveData<UserData> userDataMutableLiveData = new MutableLiveData<>();


    public void getUserData(){
        String UID = firebaseAuth.getCurrentUser().getUid().toString();
        firebaseFirestore.collection("User").document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    UserData userData = task.getResult().toObject(UserData.class);
                    userDataMutableLiveData.setValue(userData);
                }
            }
        });

    }













}
