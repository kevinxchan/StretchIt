package me.kevinxchan.kevinxchan.stretchit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.kevinxchan.kevinxchan.stretchit.R;
import me.kevinxchan.kevinxchan.stretchit.model.Routine;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {
    private List<Routine> routines;

    public RoutineAdapter(List<Routine> routines) {
        this.routines = routines;
    }

    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routine_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder viewHolder, int i) {
        viewHolder.routineName.setText(routines.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView routineName;

        public ViewHolder(View itemView) {
            super(itemView);
            routineName = (TextView) itemView.findViewById(R.id.routineName);
        }
    }
}
