package me.kevinxchan.kevinxchan.stretchit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.kevinxchan.kevinxchan.stretchit.R;
import me.kevinxchan.kevinxchan.stretchit.model.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<Exercise> exercises;
    private View.OnClickListener onClickListener;

    public ExerciseAdapter(List<Exercise> exercises, View.OnClickListener onClickListener) {
        this.exercises = exercises;
        this.onClickListener = onClickListener;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ViewHolder viewHolder, int i) {
        if (viewHolder == null)
            return;
        Exercise exercise = exercises.get(i);
        if (exercise != null) {
            viewHolder.exerciseName.setText(exercise.getName());
            viewHolder.exerciseCategory.setText(exercise.getCategory().toString());
            viewHolder.exerciseDuration.setText(exercise.getDuration());
            viewHolder.itemView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (exercises == null)
            return 0;
        return exercises.size();
    }

    public void setExerciseList(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView exerciseName;
        public TextView exerciseCategory;
        public TextView exerciseDuration;

        ViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseCategory = itemView.findViewById(R.id.exerciseCategory);
            exerciseDuration = itemView.findViewById(R.id.exerciseDuration);
        }
    }
}
