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
    private View.OnClickListener onClickListener;
    private List<Routine> routines;

    public RoutineAdapter(List<Routine> routines, View.OnClickListener onClickListener) {
        this.routines = routines;
        this.onClickListener = onClickListener;
    }

    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.routine_row, viewGroup, false);
        return new RoutineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder viewHolder, int i) {
        if (viewHolder == null)
            return;
        Routine routine = routines.get(i);
        if (routine != null) {
            viewHolder.routineName.setText(routine.getName());
            viewHolder.numTimesUsed.setText("Times used: " + routines.get(i).getNumTimesUsed());
            viewHolder.itemView.setOnClickListener(onClickListener);
            viewHolder.itemView.setTag(routine);
        }
    }

    @Override
    public int getItemCount() {
        if (routines == null)
            return 0;
        return routines.size();
    }

    public void setRoutineList(List<Routine> routines) {
        this.routines = routines;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView routineName;
        public TextView numTimesUsed;

        ViewHolder(View itemView) {
            super(itemView);
            routineName = (TextView) itemView.findViewById(R.id.routineName);
            numTimesUsed = (TextView) itemView.findViewById(R.id.numTimesUsed);
        }
    }
}
