package org.helmo.memories.view.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    protected static final String RESULT_DATE = "result_date";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DatePickerDialog.OnDateSetListener dateListener = (datePicker, year, month, day) -> {
            Bundle result = new Bundle();
            result.putSerializable("bundleKey", new GregorianCalendar(year, month, day).getTime());
            getParentFragmentManager().setFragmentResult(RESULT_DATE, result);
        };

        Calendar calendar = Calendar.getInstance();
        if(getArguments() != null) {
            Date date = (Date) getArguments().getSerializable(ARG_DATE);
            calendar.setTime(date);
        }
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(requireContext(), dateListener, initialYear, initialMonth, initialDay);
    }

    public static DatePickerFragment newInstance(Date date) {
        DatePickerFragment dpf = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DATE, date);
        dpf.setArguments(bundle);
        return dpf;
    }
}
