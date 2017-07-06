package com.knoxpo.personinfo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.knoxpo.personinfo.R;
import com.knoxpo.personinfo.misc.Util;
import com.knoxpo.personinfo.model.Person;

import java.util.Date;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class DetailFragment extends Fragment
        implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>,
        TextWatcher{
    private static final String
            TAG = DetailFragment.class.getSimpleName(),
            EXTRA_DATE = TAG+".EXTRA_DATE",
            ARGS_PERSON_ID = TAG + ".PERSON_ID",
            ARGS_PERSON_URI = TAG + ".ARGS_PERSON_URI";
    private static final int
            REQUEST_SELECT_DATE = 0,
            CURSOR_LOADER_ID=1;

    private EditText mFnameET,mLnameET;
    private Button mSaveBtn,mCancleBtn,mDobBtn;
    private Person mPerson;
    private long currentPersonId=-1;
    private Uri currentPersonUri=null;
    private boolean isUpdate = false;

    public static DetailFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ARGS_PERSON_ID,id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailFragment newInstance(Uri data) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_PERSON_URI,data);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args!=null){
           currentPersonUri = args.getParcelable(ARGS_PERSON_URI);
        }
        if(currentPersonUri!=null){
            isUpdate = true;
            getLoaderManager().initLoader(CURSOR_LOADER_ID,args,this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri data = args.getParcelable(ARGS_PERSON_URI);
        return new CursorLoader(getActivity(),
                data,
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
        if(data != null) {
            data.moveToFirst();
            mPerson = Util.getPerson(data);
            updateUI();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail,container,false);
        init(rootView);
        mFnameET.addTextChangedListener(this);
        mLnameET.addTextChangedListener(this);
        mSaveBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
        mDobBtn.setOnClickListener(this);
        return rootView;
    }

    private void init(View view){
        mFnameET = (EditText) view.findViewById(R.id.et_fname);
        mLnameET = (EditText) view.findViewById(R.id.et_lname);
        mSaveBtn = (Button) view.findViewById(R.id.btn_save);
        if(isUpdate){
            mSaveBtn.setText("Update");
        }else{
            mSaveBtn.setText("Save");
        }
        mCancleBtn = (Button) view.findViewById(R.id.btn_cancle);
        mDobBtn = (Button) view.findViewById(R.id.btn_dob);
        if(mPerson==null) {
            mPerson = new Person();
        }
    }

    private void updateUI(){
            mFnameET.setText(mPerson.getFname());
            mLnameET.setText(mPerson.getLname());
            mDobBtn.setText(mPerson.getDob().toString());
    }
    @Override
    public void onClick(View view) {
        int  rowUpdated;
        if(view.findViewById(R.id.btn_save) == mSaveBtn) {
            getPersonData();
            if(!isUpdate) {
                Util.savePerson(getActivity(), mPerson);
                Toast.makeText(getActivity(),getResources().getString(R.string.save_msg),Toast.LENGTH_SHORT).show();
            }
            else {
                rowUpdated = Util.updatePerson(getActivity(), currentPersonUri, mPerson);
                Log.d(TAG,"Updated row " + rowUpdated);
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.update_msg),
                        Toast.LENGTH_SHORT).show();
            }
        }else if(view.findViewById(R.id.btn_cancle) == mCancleBtn){
            Toast.makeText(getActivity(),
                    getActivity().getResources().getString(R.string.save_err_msg),
                    Toast.LENGTH_LONG).show();
        }else if(view.findViewById(R.id.btn_dob) == mDobBtn){
            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mPerson.getDob());
            datePickerFragment.setTargetFragment(this,REQUEST_SELECT_DATE);
            datePickerFragment.show(getFragmentManager(),EXTRA_DATE);
        }
    }
    private void getPersonData(){
        mPerson.setFname(mFnameET.getText().toString());
        mPerson.setLname(mLnameET.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SELECT_DATE && resultCode == Activity.RESULT_OK){
            if(data.hasExtra(Intent.EXTRA_RETURN_RESULT)){
                mPerson.setDob((Date)data.getSerializableExtra(Intent.EXTRA_RETURN_RESULT));
                updateUI();
            }
        }
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable==mFnameET.getText()){
            mPerson.setFname(editable.toString());
        }else if(editable == mLnameET.getText()){
            mPerson.setLname(editable.toString());
        }

    }
}
