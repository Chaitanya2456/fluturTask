package com.example.android.fluturtask;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Context mContext = this;
    private ImageView img;
    private int xDelta;
    private int yDelta;
    private ViewGroup rootLayout;
    private static final String TAG_X = "positionX";
    private static final String TAG_Y = "positionY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (ViewGroup)findViewById(R.id.view_root);
        img = (ImageView)findViewById(R.id.imageView);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.nav_home){
                    Toast.makeText(mContext, "Home", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.nav_prev_solution){
                    Toast.makeText(mContext, "Previous Solution", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.nav_clue){
                    Toast.makeText(mContext, "Clue(Free)", Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.nav_solution){
                    Toast.makeText(mContext, "Solution", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(400, 400);
        img.setLayoutParams(layoutParams);
        img.setOnTouchListener(new ChoiceTouchListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final class ChoiceTouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent event){
            final int X = (int) event.getRawX();
            Log.d(TAG_X, String.valueOf(X));
            final int Y = (int) event.getRawY();
            Log.d(TAG_Y, String.valueOf(Y));
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - xDelta;
                    layoutParams.topMargin = Y - yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }
}
