package me.kevinxchan.kevinxchan.stretchit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import me.kevinxchan.kevinxchan.stretchit.adapters.RoutineAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;
import me.kevinxchan.kevinxchan.stretchit.model.RoutineViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RoutineViewModel viewModel;
    private RoutineAdapter routineAdapter;
    private RecyclerView.OnScrollListener scrollListener;

    private FloatingActionMenu floatingActionMenuMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(getString(R.string.app_name));

        initView();
    }

    private void initView() {
        floatingActionMenuMain = findViewById(R.id.floatingActionMenuMain);

        FloatingActionButton createRoutinesBtn = findViewById(R.id.floatingActionItemAddRoutine);
        createRoutinesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkCreateRoutine = new Intent(getApplicationContext(), RoutineNameActivity.class);
                startActivity(linkCreateRoutine);
            }
        });

        final RecyclerView routinesRecyclerView = findViewById(R.id.routinesRecyclerView);
        scrollListener = initScrollListener();
        routinesRecyclerView.addOnScrollListener(scrollListener);

        routineAdapter = new RoutineAdapter(new ArrayList<Routine>(), this);
        routinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        routinesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        routinesRecyclerView.setAdapter(routineAdapter);

        viewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);
        viewModel.getRoutinesList().observe(this, new android.arch.lifecycle.Observer<List<Routine>>() {
            @Override
            public void onChanged(@Nullable List<Routine> routines) {
                routineAdapter.setRoutineList(routines);
            }
        });
    }

    private RecyclerView.OnScrollListener initScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    floatingActionMenuMain.showMenu(true);
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if ((dy > 0 || dy < 0) && floatingActionMenuMain.isShown())
                    floatingActionMenuMain.hideMenu(true);
                super.onScrolled(recyclerView, dx, dy);
            }
        };
    }

    private void setToolbar(@NonNull String string) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(string);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.d("ON_CLICK_ROUTINE", view.getTag().toString());
        Routine routine = (Routine) view.getTag();
        Intent intent = new Intent(getApplicationContext(), AddExercisesActivity.class);
        intent.putExtra("ROUTINE_ID", routine.getRoutineID());
        startActivity(intent);
    }
}
