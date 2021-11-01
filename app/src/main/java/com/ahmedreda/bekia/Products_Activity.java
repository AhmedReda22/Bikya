package com.ahmedreda.bekia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedreda.bekia.Adapters.ViewPagerAdapter;
import com.ahmedreda.bekia.Fragments.EditFragment;
import com.ahmedreda.bekia.Fragments.UserFragment;
import com.ahmedreda.bekia.Fragments.fragment_electronic;
import com.ahmedreda.bekia.Fragments.fragment_metal;
import com.ahmedreda.bekia.Fragments.fragment_paper;
import com.ahmedreda.bekia.Fragments.fragment_plastic;
import com.ahmedreda.bekia.ViewModels.UserViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Products_Activity extends AppCompatActivity  {

    private TabLayout tabLayout ;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton ;
    private TextView pointer ;
    private Button about ;
    private CircleImageView User ;
    private int [] images = {R.drawable.plastic,R.drawable.plug,
    R.drawable.blacksmith,R.drawable.document};
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    UserViewModel userViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_);
        tabLayout = findViewById(R.id.tablayout_id);
        appBarLayout = findViewById(R.id.appBar);
        viewPager = findViewById(R.id.viewpager_id);
        floatingActionButton = findViewById(R.id.btn);
        about = findViewById(R.id.about);
        User = findViewById(R.id.user);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Adding Fragments
        adapter.AddFragment(new fragment_plastic(),  "بلاستيك");
        adapter.AddFragment(new fragment_electronic(), "ألكترونيات");
        adapter.AddFragment(new fragment_metal(), "معادن");
        adapter.AddFragment(new fragment_paper(), "ورقيات");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(images[i]);
        }

        String UID = firebaseAuth.getCurrentUser().getUid().toString();
        storageReference.child("ProfileImage").child(UID+".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull  Task<Uri> task) {
                String imageUri = task.getResult().toString();
                Glide.with(Products_Activity.this).asBitmap().load(imageUri).placeholder(R.drawable.user)
                        .into(User);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Products_Activity.this,CardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                thread.start();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container,userFragment).addToBackStack(null)
                        .commit();
            }
        });



    }





}