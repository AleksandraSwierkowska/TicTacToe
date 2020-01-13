package com.example.kolkokrzyzyk.ui.home;
public class Board {

    private int[][] board;


    public Board(int size){
        board = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = -1;
            }
        }

    }
    public void turn(int field, int turn){
        int row = field/3;
        int col = field%3;
        board[row][col] = turn;
    }
    public boolean check(){
        for(int i = 0; i<3; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != -1){
                return true;
            }
        }
        for(int i = 0; i<3; i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != -1){
                return true;
            }
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != -1){
            return true;
        }
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != -1){
            return true;
        }
        return false;
    }
    public boolean areAllMovesDone(){
        for (int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if (board[i][j] == -1){return false;}
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }


}

