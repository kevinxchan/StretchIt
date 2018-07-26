package me.kevinxchan.kevinxchan.stretchit.model;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.kevinxchan.kevinxchan.stretchit.R;
import me.kevinxchan.kevinxchan.stretchit.adapters.RoutineAdapter;

import java.util.List;

public class RoutineListFragment extends Fragment {
    private RoutineAdapter routineAdapter;
    private RoutineViewModel routineViewModel;
    private Context context;

    public static RoutineListFragment newInstance() {
        return new RoutineListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
//        routineAdapter = new RoutineAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.routinesRecyclerView);
        recyclerView.setAdapter(routineAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    private void initData() {
        routineViewModel = ViewModelProviders.of(this).get(RoutineViewModel.class);
        routineViewModel.getRoutinesList().observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(@Nullable List<Routine> routines) {
                routineAdapter.setRoutineList(routines);
            }
        });
    }
}
