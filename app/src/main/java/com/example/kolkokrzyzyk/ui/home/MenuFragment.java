package com.example.kolkokrzyzyk.ui.home;

import android.animation.ValueAnimator;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.kolkokrzyzyk.R;

public class MenuFragment extends Fragment {
    private static final String TAG = "MenuFragment";
    private HomeViewModel homeViewModel;
    private Button newGame;
    private Button settings;
    private Button newGame2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        Animation animation = new Animation(root);

        newGame = root.findViewById(R.id.button1);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.setWithWho(false);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_game)
                ;}
        });
        settings = root.findViewById(R.id.button3);
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_settings);
            }
        });
        newGame2 = root.findViewById(R.id.button2);
        newGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.setWithWho(true);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_game);
            }
        });



        return root;
    }
}