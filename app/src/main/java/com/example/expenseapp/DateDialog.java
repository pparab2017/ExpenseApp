package com.example.expenseapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by pushparajparab on 9/8/16.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText textDate;

    public EditText getTextDate() {
        return textDate;
    }

    public void setTextDate(EditText textDate) {
        this.textDate = textDate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       final Calendar c =  Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        return  new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = (monthOfYear + 1)  + "/" + dayOfMonth  + "/" + year;
        this.textDate.setText(date);
    }
}
