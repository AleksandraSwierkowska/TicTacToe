package com.example.kolkokrzyzyk.ui.home;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.kolkokrzyzyk.R;


public class GameFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView textView;
    private View root;
    private Button[] buttons;
    private int i = 0;
    private int[] img;
    private int sym;
    private Button reset;
    private Button back;
    private boolean allow;
    private Integer value;
    private static Handler handler;




    @SuppressLint("HandlerLeak")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allow = true;
        value = 0;
        homeViewModel =
                ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_game_normal, container, false);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                value = b.getInt("KEY");
                if(value == 1332){
                    homeViewModel.setStop(true);

                }

            }
        };


        final Timer timer = new Timer(homeViewModel.getTime().getValue(), handler);
        timer.run();



        if (homeViewModel.getSize().getValue() == 0){
            root = inflater.inflate(R.layout.fragment_game_small, container, false);
            textView = root.findViewById(R.id.text_slideshow);}
        else if(homeViewModel.getSize().getValue() == 2){
            root = inflater.inflate(R.layout.fragment_game_big, container, false);
            textView = root.findViewById(R.id.text_tools);
        }//max
        else{
            root = inflater.inflate(R.layout.fragment_game_normal, container, false);
            textView = root.findViewById(R.id.text_gallery);
        }
        Animation animation = new Animation(root);
        reset = root.findViewById(R.id.reset);
        back = root.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
            }
        });
        homeViewModel.setTurn(i);
        homeViewModel.getStop().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (homeViewModel.getStop().getValue() == true){
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder
                        .setTitle("KONIEC GRY")
                        .setMessage("Czas się skończył, może następnym razem się uda")
                        .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
                            }
                        })
                        .setPositiveButton("Zagraj ponownie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
                            }
                        });

                builder.create().show();}

                timer.interrupt();
                if (homeViewModel.getStop().getValue() == true){
                    homeViewModel.setStop(false);}

            }
        });
        buttons = new Button[9];
        img = new int[9];
        final Board board = new Board(3);

        for (int k = 0; k < 9; k++) {

            String id = "b" + (k + 1);
            final int resID = getResources().getIdentifier(id, "id", textView.getContext().getOpPackageName());
            final int kCopy = k;
            buttons[k] = root.findViewById(resID);
            buttons[k].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    i++;
                    homeViewModel.setTurn(i);
                    sym = homeViewModel.getSymbol().getValue();

                    homeViewModel.getTurn().observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            textView.setText("KOLEJ GRACZA NR " + (integer%2+1));
                            if (homeViewModel.getWithWho().getValue()){
                                if (sym == 0) {
                                    if (img[kCopy] == 0) {

                                        if (integer % 2 == 1) {
                                            img[kCopy] = R.drawable.r2d2;
                                        } else {
                                            img[kCopy] = R.drawable.trooper;
                                        }
                                    }
                                }
                                else if (sym == 1){
                                    if (img[kCopy] == 0) {

                                        if (integer % 2 == 1) {
                                            img[kCopy] = R.drawable.circle;
                                        } else {
                                            img[kCopy] = R.drawable.cross;
                                        }
                                    }
                                }
                                else {
                                    if (img[kCopy] == 0) {
                                        if (integer % 2 == 1) {
                                            img[kCopy] = R.drawable.windows;
                                        } else {
                                            img[kCopy] = R.drawable.linux;
                                        }
                                    }
                                }
                            }
                            else{
                                if (sym == 0) {
                                    img[kCopy] = R.drawable.r2d2;
                                }
                                else if (sym == 1){
                                    img[kCopy] = R.drawable.circle;
                                }
                                else {
                                    img[kCopy] = R.drawable.windows;
                                }
                            }
                        }
                    });

                    buttons[kCopy].setBackgroundResource(img[kCopy]);
                    buttons[kCopy].setEnabled(false);
                    board.turn(kCopy, homeViewModel.getTurn().getValue()%2);
                    if(board.check()){
                        int winner = (homeViewModel.getTurn().getValue()%2);
                        if (winner == 0){winner = 2;}
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                        builder
                                .setTitle("KONIEC GRY")
                                .setMessage("Wygrał gracz nr " + winner)
                                .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
                                    }
                                })
                                .setPositiveButton("Zagraj ponownie", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
                                    }
                                });

                        builder.create().show();
                        allow = false;
                        timer.interrupt();
                    }
                    else if (board.areAllMovesDone()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                        builder
                                .setTitle("KONIEC GRY")
                                .setMessage("Pojedynek był zbyt wyrównany")
                                .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
                                    }
                                })
                                .setPositiveButton("Zagraj ponownie", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
                                    }
                                });
                        builder.create().show();
                        allow = false;
                        timer.interrupt();

                    }
                    if (!homeViewModel.getWithWho().getValue() && homeViewModel.getTurn().getValue()%2==1 && allow){
                        ComputerPlay(board, sym);
                        homeViewModel.setTurn(i);
                        if(board.check()){
                            int winner = (homeViewModel.getTurn().getValue()%2);
                            if (winner == 0){winner = 2;}
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                            builder
                                    .setTitle("KONIEC GRY")
                                    .setMessage("Wygrał gracz nr " + winner)
                                    .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
                                        }
                                    })
                                    .setPositiveButton("Zagraj ponownie", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
                                        }
                                    });

                            builder.create().show();
                            timer.interrupt();

                        }
                        else if (board.areAllMovesDone()){

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                            builder
                                    .setTitle("KONIEC GRY")
                                    .setMessage("Pojedynek był zbyt wyrównany")
                                    .setNeutralButton("Powrót", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_to_nav_home);
                                        }
                                    })
                                    .setPositiveButton("Zagraj ponownie", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_game_self);
                                        }
                                    });
                            builder.create().show();
                            timer.interrupt();

                        }
                    }

                }
            });
        }


        return root;
    }


    public void ComputerPlay(Board board, int sym){
        int x;
        i++;
        for (int i = 0; i<board.getBoard().length; i++){
            for (int j = 0; j<board.getBoard().length; j++){
                if (board.getBoard()[i][j] == -1){
                    x = board.getBoard().length*i+j;
                    board.turn(x, 0);
                    if (sym == 0) {
                        img[x] = R.drawable.trooper;
                    }
                    else if (sym == 1){
                        img[x] = R.drawable.cross;
                    }
                    else {
                        img[x] = R.drawable.linux;
                    }
                    buttons[x].setBackgroundResource(img[x]);
                    buttons[x].setEnabled(false);
                    return;
                }

            }
        }
    }

}