package me.kevinxchan.kevinxchan.stretchit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import me.kevinxchan.kevinxchan.stretchit.adapters.RoutineAdapter;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionMenu floatingActionMenuMain;
    private FloatingActionButton createRoutinesBtn;
    private RecyclerView routinesRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<Routine> routines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Stretch It");

        floatingActionMenuMain = (FloatingActionMenu) findViewById(R.id.floatingActionMenuMain);
        createRoutinesBtn = (FloatingActionButton) findViewById(R.id.floatingActionItemAddRoutine);
        routines = new ArrayList<>();
        routinesRecyclerView = initRecyclerView();

        for (int i = 0; i < 10; i++) {
            Routine routine = new Routine("Foo");
            routines.add(routine);
        }

        createRoutinesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkCreateRoutine = new Intent(getApplicationContext(), RoutineNameActivity.class);
                startActivity(linkCreateRoutine);
            }
        });
        routinesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
    }

    private RecyclerView initRecyclerView() {
        routinesRecyclerView = (RecyclerView) findViewById(R.id.routinesRecyclerView);
        routinesRecyclerView.setLayoutManager(new LinearLayoutManager(null));
        adapter = new RoutineAdapter(routines);
        routinesRecyclerView.setAdapter(adapter);
        return routinesRecyclerView;
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
}
