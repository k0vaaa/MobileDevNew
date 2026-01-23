package ru.mirea.kovalikaa.pocketdictionary.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.mirea.kovalikaa.pocketdictionary.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);

        bottomNav = findViewById(R.id.bottom_navigation_view);

        String userEmail = viewModel.getCurrentUserEmail();
        boolean isGuest = (userEmail == null);

        if (isGuest) {
            bottomNav.getMenu().findItem(R.id.navigation_favorites).setVisible(false);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String tag = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_search) {
                selectedFragment = new SearchFragment();
                tag = "SEARCH_FRAGMENT";
            } else if (itemId == R.id.navigation_favorites) {
                if (isGuest) return false;

                selectedFragment = new FavoritesFragment();
                tag = "FAVORITES_FRAGMENT";
            } else if (itemId == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
                tag = "PROFILE_FRAGMENT";
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment, tag)
                        .addToBackStack(tag)
                        .commit();
            }
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new SearchFragment(), "SEARCH_FRAGMENT")
                    .commit();
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();

                    fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            Fragment currentFragment = fm.findFragmentById(R.id.fragment_container_view);
                            if (currentFragment instanceof SearchFragment) {
                                bottomNav.setSelectedItemId(R.id.navigation_search);
                            } else if (currentFragment instanceof FavoritesFragment) {
                                bottomNav.setSelectedItemId(R.id.navigation_favorites);
                            } else if (currentFragment instanceof ProfileFragment) {
                                bottomNav.setSelectedItemId(R.id.navigation_profile);
                            }
                            fm.removeOnBackStackChangedListener(this);
                        }
                    });
                } else {
                    finish();
                }
            }
        });
    }
}