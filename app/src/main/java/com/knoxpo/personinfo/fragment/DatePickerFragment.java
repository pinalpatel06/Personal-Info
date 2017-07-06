package com.knoxpo.personinfo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.knoxpo.personinfo.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tejas Sherdiwala on 12/13/2016.
 * &copy; Knoxpo
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String
                        TAG = DatePickerFragment.class.getSimpleName(),
                        ARGS_DATE = TAG + ".ARGS_DATE";

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_DATE,date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*Bundle args = getArguments();
        Calendar calender = Calendar.getInstance();
        if(args!=null && args.getSerializable(ARGS_DATE)!=null){
            Date date = (Date) args.getSerializable(ARGS_DATE);
            calender.setTime(date);
        }
        return new DatePickerDialog(getActivity(),this,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DATE));*/

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.fragment_date_picker, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(2025);
        yearPicker.setValue(year);
        builder.setTitle("Select Month & Year");
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatePickerFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int yr, int mn, int dt) {
        Calendar calender = Calendar.getInstance();
        calender.set(yr,mn,dt);
        Date date = calender.getTime();

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_RETURN_RESULT,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
