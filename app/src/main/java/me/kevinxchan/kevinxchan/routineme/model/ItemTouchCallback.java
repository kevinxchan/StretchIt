package me.kevinxchan.kevinxchan.routineme.model;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import me.kevinxchan.kevinxchan.routineme.R;

public abstract class ItemTouchCallback extends ItemTouchHelper.Callback {
    // graphics
    private Drawable deleteIcon;
    private Drawable background;
    private int intrinsicWidth;
    private int intrinsicHeight;
    private Paint clearPaint;

    public ItemTouchCallback(Context context) {
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp);
        background = new ColorDrawable(Color.RED); // red background
        intrinsicWidth = deleteIcon.getIntrinsicWidth();
        intrinsicHeight = deleteIcon.getIntrinsicHeight();
        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int top = itemView.getTop();
        int bottom = itemView.getBottom();
        int right = itemView.getRight();
        int left = itemView.getLeft();
        int itemHeight = bottom - top;
        boolean isCanceled = dX == 0f && !isCurrentlyActive;

        if (isCanceled) {
            clearCanvas(c, right + dX, (float) top, (float) right, (float) bottom);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false);
            return;
        }

        // sliding background color
        background.setBounds(right + (int) dX, top, right, bottom);
        background.draw(c);

        // Calculate position of delete icon
        int deleteIconTop = top + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = right - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = right - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        // Draw the delete icon. overlay on background
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        c.drawRect(left, top, right, bottom, clearPaint);
    }
}
