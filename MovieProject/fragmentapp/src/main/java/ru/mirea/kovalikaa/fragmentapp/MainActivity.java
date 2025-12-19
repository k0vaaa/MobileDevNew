package ru.mirea.kovalikaa.fragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int MY_STUDENT_NUMBER = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("student_number", MY_STUDENT_NUMBER);

            TodoListFragment todoListFragment = new TodoListFragment();
            todoListFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, todoListFragment)
                    .commit();
        }
    }
}