package com.example.android.recyclerviewproject.Helper;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import com.example.android.recyclerviewproject.Adapter.ExampleAdapter;
import com.example.android.recyclerviewproject.Adapter.ServiceAdapter;

public class RecyclerServiceTouchHelper extends ItemTouchHelper.SimpleCallback{

    private RecyclerItemTouchHelperListener listener;

    public RecyclerServiceTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener thisListener) {
        super(dragDirs, swipeDirs);
        listener = thisListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((ServiceAdapter.ServiceViewHolder) viewHolder).mCardView;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((ServiceAdapter.ServiceViewHolder) viewHolder).mCardView;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX , dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((ServiceAdapter.ServiceViewHolder) viewHolder).mCardView;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((ServiceAdapter.ServiceViewHolder) viewHolder).mCardView;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX , dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }


    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
