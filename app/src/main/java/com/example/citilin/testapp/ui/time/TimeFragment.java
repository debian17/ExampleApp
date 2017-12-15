package com.example.citilin.testapp.ui.time;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.citilin.testapp.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class TimeFragment extends Fragment implements TimePresenter.View{

    private TextView current_date;
    private TextView current_time;
    private TimePresenter timePresenter;

    private static String DATE_PATTERN = "cccc, d LLLL yyyyг.";
    private static String TIME_PATTERN = "HH:mm:ss";
    private static String DATE_TEXT = "На календаре ";
    private static String TIME_TEXT = "Текущее время ";

    public TimeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timePresenter = new TimePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        current_date = (TextView) view.findViewById(R.id.current_date);
        current_time = (TextView) view.findViewById(R.id.current_time);

        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2017, 10, 4, 12, 13,14,123, ZoneId.systemDefault());
        OffsetDateTime offsetDateTime = OffsetDateTime.now();

        current_date.setText(DATE_TEXT.concat(localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN))));
        current_time.setText(TIME_TEXT.concat(localDateTime.format(DateTimeFormatter.ofPattern(TIME_PATTERN))));

        timePresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timePresenter.detachView();
    }
}
