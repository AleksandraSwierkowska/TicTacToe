package com.example.kolkokrzyzyk.ui.home;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.kolkokrzyzyk.R;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private ShareViewModel shareViewModel;
    private Spinner spinner;
    private RadioGroup radioGroup;
    private Button back2;
    private SeekBar seekBar;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        homeViewModel =
                ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Animation animation = new Animation(root);

        back2 = root.findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_settings_to_nav_home);
            }
        });
        textView = root.findViewById(R.id.timeChoose2);
        seekBar = root.findViewById(R.id.seekBar);
        spinner = root.findViewById(R.id.spinner1);
        radioGroup = root.findViewById(R.id.radio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(),
                R.array.sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                if (item.equals("Pomniejszona")){
                    homeViewModel.setSize(0);
                }
                else if (item.equals("Normalna")){
                    homeViewModel.setSize(1);
                }
                else{
                    homeViewModel.setSize(2);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.programista:
                        homeViewModel.setSymbol(2);
                        break;
                    case R.id.starwars:
                        homeViewModel.setSymbol(0);
                        break;
                    case R.id.tradycyjne:
                        homeViewModel.setSymbol(1);
                        break;
                }
            }

        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = seekBar.getProgress();
                int min = (int) time/60;
                int sec = (int) time % 60;
                String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                textView.setText(timeFormat);
                homeViewModel.setTime(time);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return root;
    }

    public static class ShareViewModel extends ViewModel {

        private MutableLiveData<String> mText;

        public ShareViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is share fragment");
        }

        public LiveData<String> getText() {
            return mText;
        }
    }
}