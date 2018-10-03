package com.mvryan.katalogfilm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.utils.notif.DailyNotif;
import com.mvryan.katalogfilm.utils.notif.NowPlayingNotif;

/**
 * Created by mvryan on 24/08/18.
 */

public class SettingFragment extends Fragment {

    Button btnChangeLang;
    RadioGroup radioDailyNotif,radioNowPlayingNotif;
    TextView tvLang;

    DailyNotif dailyNotif;
    NowPlayingNotif nowPlayingNotif;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailyNotif=new DailyNotif();
        nowPlayingNotif=new NowPlayingNotif();

        tvLang = view.findViewById(R.id.tv_lang);
        tvLang.setText(R.string.tv_lang);
        btnChangeLang = view.findViewById(R.id.change_lang);
        btnChangeLang.setText(R.string.lang);
        radioDailyNotif = view.findViewById(R.id.daily_notif);
        radioDailyNotif.check(R.id.off_daily);
        radioNowPlayingNotif = view.findViewById(R.id.now_playing_notif);
        radioDailyNotif.check(R.id.off_now_playing);

        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        radioDailyNotif.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.on_daily:
                        dailyNotif.onReminder(getActivity());
                        break;
                    case R.id.off_daily:
                        dailyNotif.offReminder(getActivity());
                        break;
                    default:
                        break;

                }
            }
        });

        radioNowPlayingNotif.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.on_now_playing:
                        nowPlayingNotif.onReminder(getActivity());
                        break;
                    case R.id.off_now_playing:
                        nowPlayingNotif.offReminder(getActivity());
                        break;
                    default:
                        break;

                }
            }
        });
    }

}
