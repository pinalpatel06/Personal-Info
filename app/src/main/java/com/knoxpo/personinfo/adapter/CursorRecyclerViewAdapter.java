package com.knoxpo.personinfo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

import com.knoxpo.personinfo.R;
import com.knoxpo.personinfo.data.PersonContract;

/**
 * Created by Tejas Sherdiwala on 12/13/2016.
 * &copy; Knoxpo
 */

public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
                extends RecyclerView.Adapter<VH>{
    private Context mContext;
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIdColumn;
    private DataSetObserver mDataSetObserver;

    public CursorRecyclerViewAdapter(Context context,Cursor cursor){
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid? mCursor.getColumnIndex(PersonContract.PersonEntry._ID):-1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if(mCursor!=null){
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    public Cursor getCursor(){
        return  mCursor;
    }

    @Override
    public int getItemCount() {

        if(mDataValid && mCursor!= null){
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        if(mDataValid && mCursor != null && mCursor.moveToPosition(position)){
           // return super.getItemId(position);
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    public abstract void onBindView(VH holder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if(!mDataValid){
            throw new IllegalStateException(mContext.getResources().getString(R.string.invalid_cursor_err_msg));
        }

        if(!mCursor.moveToPosition(position)){
            throw new IllegalStateException(mContext.getResources().getString(R.string.invalid_cursor_pos_err_msg,position));
        }

        onBindView(holder,mCursor);
    }

    public void changeCursor(Cursor cursor){
        Cursor oldCursor = swapCursor(cursor);
        if(oldCursor!=null){
            oldCursor.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor){

        if(mCursor == newCursor){
            return  null;
        }

        Cursor oldCursor = mCursor;
        if(oldCursor!=null && mDataSetObserver != null){
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if(mCursor!=null){
            if(mDataSetObserver!=null){
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = mCursor.getColumnIndexOrThrow(PersonContract.PersonEntry._ID);
            mDataValid = true;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver{
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid=true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }
}
