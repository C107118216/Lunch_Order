package com.example.lunch_order;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lunch_order.ui.home.HomeFragment;
import com.example.lunch_order.ui.logout.LogoutFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    /* 透過從 LoginFragment得到是否有登入的結果來判斷要用哪個介面 */
    Boolean is_Login = false;
    static String user_ID;
    static String user_Name;
    static String user_Email;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        /* 確認說是否有捕抓到資料(沒有則 is_Login為空字串) */
        if (bundle != null) {
            is_Login = (Boolean) bundle.getBoolean("is_Login");
            user_ID = (String) bundle.getString("id");
            user_Name = (String) bundle.getString("name");
            user_Email = (String) bundle.getString("email");
        }

        // 在LoginFragment中，如果is_Login為True代表登入成功
        if (is_Login) {
            // 將會是登入後的介面
            setContentView(R.layout.activity_dashboard);

        } else {
            setContentView(R.layout.activity_main);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // 在LoginFragment中，如果is_Login為True代表登入成功
        if (is_Login) {
            // 將會是登入後的介面
            drawer = findViewById(R.id.drawer_dashboard_layout);
        } else {
            drawer = findViewById(R.id.drawer_layout);
        }


        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();


        if (is_Login) {
            /* 設立一個headerView來對應到我的 Nav */
            // https://stackoverflow.com/questions/34973456/how-to-change-text-of-a-textview-in-navigation-drawer-header
            View headerView = navigationView.getHeaderView(0);
            /* 此時就可以修改 nav_header_main的文字了(沒有上面那行會Error) */
            TextView navName = (TextView) headerView.findViewById(R.id.nav_header_title);
            TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);
            navName.setText(user_Name);
            navEmail.setText(user_Email);
        }


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // 設立離開
//    https://www.youtube.com/watch?v=7CnhC5-68i4
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("你確定要離開了嗎");
        builder.setCancelable(true);
        builder.setNegativeButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                System.exit(0);
            }
        });

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }










}








