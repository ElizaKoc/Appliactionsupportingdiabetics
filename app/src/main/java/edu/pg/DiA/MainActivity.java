package edu.pg.DiA;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.User;
import edu.pg.DiA.ui.diet.DietFragment;
import edu.pg.DiA.ui.glucose_measurements.AddNewGlucoseMeasurementFragment;
import edu.pg.DiA.ui.glucose_measurements.GlucoseMeasurementsFragment;
import edu.pg.DiA.ui.help.HelpFragment;
import edu.pg.DiA.ui.journal.JournalFragment;
import edu.pg.DiA.ui.literature.LiteratureFragment;
import edu.pg.DiA.ui.medicines.MedicineFragment;
import edu.pg.DiA.ui.medicines.MedicinesFragment;
import edu.pg.DiA.ui.profile.ProfileFragment;
import edu.pg.DiA.ui.profile_list.ProfileListFragment;
import edu.pg.DiA.ui.reports.ReportsFragment;
import edu.pg.DiA.ui.schedule.ScheduleFragment;
import edu.pg.DiA.ui.settings.SettingsFragment;
import edu.pg.DiA.ui.weight_measurements.WeightMeasurementsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ReportFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    public AppDatabase db;
    private AppBarConfiguration mAppBarConfiguration;
    private  Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private MenuItem setHighlightProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getInstance(getApplicationContext());


        int reminderId = getIntent().getIntExtra("reminder_id", 0);
        String alarmType = getIntent().getStringExtra("alarm_type");

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int userId = sharedPref.getInt(getString(R.string.preference_file_key), 0);

        if(reminderId != 0) {
            int newUserId = db.reminderDao().getUserId(reminderId);

            if(userId != newUserId) {
                userId = newUserId;

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt((context.getString(R.string.preference_file_key)), userId);
                editor.apply();
            }
        }

        if(userId != 0) {
            User user = db.userDao().getUser(userId);
            User.setUser(user);

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

            drawer = findViewById(R.id.drawer_layout);
            drawerToggle = setupDrawerToggle();
            // Setup toggle to display hamburger icon with animation
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
            drawer.addDrawerListener(drawerToggle);

            NavigationView navigationView = findViewById(R.id.nav_view);
            setupDrawerContent(navigationView);
            setHighlightProfile = navigationView.getMenu().findItem(R.id.nav_profile);
            setHighlightProfile.setChecked(true);

            View hView =  navigationView.getHeaderView(0);
            TextView navFirstName = (TextView) hView.findViewById(R.id.nav_first_name);
            navFirstName.setText(User.getCurrentUser().firstName);

            fragmentManager = getSupportFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.change_list_fragment, new ProfileFragment(), "profile").commit();

            if(alarmType != null) {

                Bundle args = new Bundle();

                if (alarmType.equals("pomiar glukozy")) {

                    args.putInt("reminder_id", reminderId);
                    AddNewGlucoseMeasurementFragment addNewGlucoseMeasurementFragment = new AddNewGlucoseMeasurementFragment();
                    addNewGlucoseMeasurementFragment.setArguments(args);
                    fragmentManager.beginTransaction().replace(R.id.change_list_fragment, addNewGlucoseMeasurementFragment).commit();
                }
                else if(alarmType.equals("lekarstwo")) {

                    int medicineId = db.medicineReminderDao().getMedicineId(reminderId);
                    args.putInt("medicine_id", medicineId);
                    MedicineFragment medicineFragment = new MedicineFragment();
                    medicineFragment.setArguments(args);
                    fragmentManager.beginTransaction().replace(R.id.change_list_fragment, medicineFragment).commit();
                }
                else {
                    fragmentManager.beginTransaction().replace(R.id.change_list_fragment, new ProfileFragment(), "profile").commit();
                }
            }
            else {
                fragmentManager.beginTransaction().replace(R.id.change_list_fragment, new ProfileFragment(), "profile").commit();
            }

        } else {

            Intent intent = new Intent(MainActivity.this, LoadProfilesActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                ProfileFragment profile = (ProfileFragment) fragmentManager.findFragmentByTag("profile");
                LiteratureFragment literature = (LiteratureFragment) fragmentManager.findFragmentByTag("literature");
                if (profile != null && profile.isVisible()) {
                    super.onBackPressed();
                } else if (literature != null && literature.isVisible()) {
                    if (literature.webView.canGoBack()) {
                        literature.webView.goBack();
                    } else {
                        fragmentManager.beginTransaction().replace(R.id.change_list_fragment, new ProfileFragment(), "profile").commit();

                        if (setHighlightProfile != null) {
                            setHighlightProfile.setChecked(true);
                        }
                    }
                } else {
                    fragmentManager.beginTransaction().replace(R.id.change_list_fragment, new ProfileFragment(), "profile").commit();

                    if (setHighlightProfile != null) {
                        setHighlightProfile.setChecked(true);
                    }
                }
            }

            showBackButton(false);
            setTitle(getTitle());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //close navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        String tag = "";
        Class fragmentClass;
        int id = menuItem.getItemId();
        if(id == R.id.nav_profile) {
            fragmentClass = ProfileFragment.class;
            tag = "profile";
        }
        else if(id == R.id.nav_medicines) {
            fragmentClass = MedicinesFragment.class;
        }
        else if(id == R.id.nav_glucose_measurements) {
            fragmentClass = GlucoseMeasurementsFragment.class;
        }
        else if(id == R.id.nav_weight_measurements) {
            fragmentClass = WeightMeasurementsFragment.class;
        }
        /*else if(id == R.id.nav_diet) {
            fragmentClass = DietFragment.class;
        }*/
        else if(id == R.id.nav_schedule) {
            fragmentClass = ScheduleFragment.class;
        }
        else if(id == R.id.nav_journal) {
            fragmentClass = JournalFragment.class;
        }
        else if(id == R.id.nav_literature) {
            fragmentClass = LiteratureFragment.class;
            tag = "literature";
        }
        /*else if(id == R.id.nav_reports) {
            fragmentClass = ReportsFragment.class;
        }*/
        else if(id == R.id.nav_settings) {
            fragmentClass = SettingsFragment.class;
        }
        /*else if(id == R.id.nav_help) {
            fragmentClass = HelpFragment.class;
        }*/
        else if(id == R.id.nav_profile_list) {
            fragmentClass = ProfileFragment.class;
            Intent intent = new Intent(this, LoadProfilesActivity.class);
            startActivity(intent);
            this.finish();
        }
        else {
            fragmentClass = ProfileFragment.class;
            tag = "profile";
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.change_list_fragment, fragment, (tag.equals("") ? null : tag)).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public void setDrawerLocked(boolean enabled) {
        if(enabled){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            showBackButton(true);

            drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            drawer.addDrawerListener(drawerToggle);
            drawerToggle.syncState();

        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            showBackButton(false);

            drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
            drawer.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
    }

    public void showBackButton(boolean isBack){
        drawerToggle.setDrawerIndicatorEnabled(!isBack);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isBack);
        drawerToggle.syncState();
    }
}