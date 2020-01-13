package com.example.kolkokrzyzyk.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> symbol;
    private MutableLiveData<Integer> size;
    private MutableLiveData<Integer> turn;
    private MutableLiveData<Boolean> withWho;
    private MutableLiveData<Integer> time;
    private MutableLiveData<Boolean> stop;


    public HomeViewModel() {
        turn = new MutableLiveData<>();
        size = new MutableLiveData<>();
        size.setValue(1);
        symbol = new MutableLiveData<>();
        symbol.setValue(1);
        withWho = new MutableLiveData<>();
        withWho.setValue(true);
        time = new MutableLiveData<>();
        time.setValue(60);
        stop = new MutableLiveData<>();
        //stop.setValue(false);
    }
    public void setTurn(Integer i){
        turn.setValue(i);
    }
    public LiveData<Integer> getTurn(){
        return turn;
    }
    public void setSize(Integer i){
        size.setValue(i);
    }
    public LiveData<Integer> getSize(){
        return size;
    }
    public void setSymbol(Integer i){
        symbol.setValue(i);
    }
    public LiveData<Integer> getSymbol(){
        return symbol;
    }
    public void setWithWho(Boolean i){withWho.setValue(i);}
    public LiveData<Boolean>getWithWho(){return withWho;}
    public void setTime(Integer i){time.setValue(i);}
    public LiveData<Integer>getTime(){return time;}
    public void setStop(Boolean i){stop.setValue(i);}
    public LiveData<Boolean>getStop(){return stop;}

}
