package com.knoxpo.personinfo.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.knoxpo.personinfo.R;
import com.knoxpo.personinfo.activity.DetailActivity;
import com.knoxpo.personinfo.adapter.CursorRecyclerViewAdapter;
import com.knoxpo.personinfo.data.PersonContract;
import com.knoxpo.personinfo.misc.Util;
import com.knoxpo.personinfo.model.Person;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String
            TAG = MainFragment.class.getSimpleName(),
            ARGS_SELECTED_LIST = TAG+".ARGS_SELECTED_LIST";
    private static final int CURSOR_LOADER_ID=0;

    private RecyclerView mListItemRV;
    private PersonCursorAdapter mAdapter;
    private SparseBooleanArray mSelectedPosition;
    private ActionMode mActionMode;
    private MultiSelector mMultiSelector = new MultiSelector();
    private ModalMultiSelectorCallback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector){
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getActivity().getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.action_item_delete) {
                // Need to finish the action mode before doing the following,
                // not after. No idea why, but it crashes.
                actionMode.finish();

                int rowDeleted = 0;
                if (menuItem.getItemId() == R.id.action_item_delete) {
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        //if(mSelectedPosition.get(i)) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            Util.deletePerson(getActivity(), mAdapter.getItemId(i));
                            //mSelectedPosition.delete(i);
                            rowDeleted++;
                        }
                    }
                }
                 Toast.makeText(getActivity(), getResources().getString(R.string.delete_msg, rowDeleted), Toast.LENGTH_LONG).show();
                 mMultiSelector.clearSelections();
                 return true;
            }
            return false;
            }
        };



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(mMultiSelector!=null){
            Bundle bundle = savedInstanceState;
            if(bundle!=null){
                mMultiSelector.restoreSelectionStates(bundle.getBundle(ARGS_SELECTED_LIST));
            }

            if(mMultiSelector.isSelectable()){
                if(mDeleteMode !=null){
                    mDeleteMode.setClearOnPrepare(false);
                    mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
                }
            }
        }
        super.onActivityCreated(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        init(rootView);
        mListItemRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoaderManager().initLoader(CURSOR_LOADER_ID,null,this);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_item_add:
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item,menu);
    }

    private void init(View view){
        mListItemRV = (RecyclerView) view.findViewById(R.id.rv_list);
        mAdapter = new PersonCursorAdapter(getActivity(),null);
        mSelectedPosition = new SparseBooleanArray();
        //mMultiSelector = new MultiSelector();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(ARGS_SELECTED_LIST,mMultiSelector.saveSelectionStates());

        super.onSaveInstanceState(outState);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new CursorLoader(getActivity(),
                PersonContract.PersonEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        mListItemRV.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /*@Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_action_mode,menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int rowDeleted=0;
        if(item.getItemId() == R.id.action_item_delete){
            for(int i=0;i<mAdapter.getItemCount();i++) {
                //if(mSelectedPosition.get(i)) {
                if(mMultiSelector.isSelected(i,0)){
                    Util.deletePerson(getActivity(),mAdapter.getItemId(i));
                    //mSelectedPosition.delete(i);
                    rowDeleted++;
                }
            }
            Toast.makeText(getActivity(), getResources().getString(R.string.delete_msg, rowDeleted), Toast.LENGTH_LONG).show();
            mMultiSelector.clearSelections();
            mode.finish();
            return true;
        }
        return false;

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mMultiSelector.setSelectable(false);

    }*/

    private class PersonCursorAdapter extends CursorRecyclerViewAdapter<PersonCursorAdapter.ViewHolder>{
        private LayoutInflater mLayoutInflater;

        public PersonCursorAdapter(Context context , Cursor cursor){
            super(context,cursor);
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public PersonCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mLayoutInflater.inflate(R.layout.item_list,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindView(ViewHolder holder, Cursor cursor) {
            holder.bindCursor(cursor);
        }

        public class ViewHolder extends SwappingHolder //RecyclerView.ViewHolder
                    implements View.OnClickListener,View.OnLongClickListener{

            private TextView mFnameTV;
            private Person person;
            public ViewHolder(View itemView){
                super(itemView,mMultiSelector);
                mFnameTV = (TextView) itemView.findViewById(R.id.tv_fname);
            }
            @Override
            public void onClick(View view) {

               /* if(mActionMode!=null){
                    if(view.isActivated()){
                        view.setActivated(false);
                        //mSelectedPosition.put(getAdapterPosition(),false);
                        mSelectedPosition.delete(getAdapterPosition());
                    }else {
                        view.setActivated(true);
                        mSelectedPosition.put(getAdapterPosition(),true);
                    }
                }else*/ if(!mMultiSelector.tapSelection(this)) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    Log.d(TAG, "uri" + PersonContract.PersonEntry.builtPersonUri(person.getId()));
                    intent.setData(PersonContract.PersonEntry.builtPersonUri(person.getId()));
                    startActivity(intent);
                }
               /* Log.d(TAG,"list " + mMultiSelector.getSelectedPositions());
                if(mMultiSelector.getSelectedPositions().isEmpty()){
                    mActionMode.finish();
                }*/
                if(mSelectedPosition.size()==0){
                    mActionMode.finish();
                }

            }

            @Override
            public boolean onLongClick(View view) {
                    if(!mMultiSelector.isSelectable()) {
                    mActionMode =((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);
                   // mMultiSelector.setSelectable(true);
                    mMultiSelector.setSelected(this, true);
                    return true;
                }
                //mSelectedPosition.put(getAdapterPosition(),true);
                return false;
            }

            public void bindCursor(Cursor cursor){
               // itemView.setActivated(mSelectedPosition.get(getAdapterPosition()));
                itemView.setActivated(mMultiSelector.isSelected(getAdapterPosition(),getId()));
                person = Util.getPerson(cursor);
                if(person!=null) {
                    mFnameTV.setText(person.getFname() + " " + person.getLname());
                }
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);
            }
        }
    }
}
