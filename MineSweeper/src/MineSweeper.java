import java.util.Random;
import java.util.Scanner;

public class MineSweeper{
    int row,column,mineNumber;
    String[][] map;
    String[][] board;
    boolean finish = false;

    Scanner input = new Scanner(System.in);
    Random randomMineNumber = new Random();

    public void run(){
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz!");

        //Kullanıcıdan satır ve sütun sayısını aldım.
        System.out.print("Satır Sayısını Giriniz:  ");
        row = input.nextInt();
        System.out.print("Sütun Sayısını Giriniz: ");
        column = input.nextInt();

        //Mayınları eleman sayısının çeyreği olacağı şekilde tanımladım.
        mineNumber = (row*column) / 4;

        //Mayınların konumlarını tutacak bir dizi ve kullanıcıya sunulacak bir dizi oluşturduk.
        map = new String[row][column];
        board = new String[row][column];

        //Kullanıcıdan alınan verilere göre
        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                board[i][j] = "-";
                map[i][j] = "-";
            }
        }
        //Mayınların haritaya rastgele yerleşmesi ve aynı noktaya tekrar yerleşmemesi için while döngüsü ve if kullandım.
        int count = 0;
        while(count != mineNumber){
            int rowMineNumber = randomMineNumber.nextInt(row);
            int columnMineNumber = randomMineNumber.nextInt(column);

            if(map[rowMineNumber][columnMineNumber].equals("-")){
                map[rowMineNumber][columnMineNumber] = "*";
                count++;
            }

        }
        printBoard();
        playCheck();
    }

    public void playCheck(){
        System.out.println("Oyun Başladı!");
        while(!finish){
            System.out.print("Satır Sayısı: ");
            int selectedRow = input.nextInt();
            System.out.print("Sütun Sayısı: ");
            int selectedColumn = input.nextInt();

            int mineNumber=0;
            //Kullanıcının girdiği konumda etrafındaki mayın sayısını yazdırdım.
            if(selectedRow < row && selectedColumn < column){
                if(map[selectedRow][selectedColumn].equals("-") && board[selectedRow][selectedColumn].equals("-")){
                    for(int i = selectedRow - 1; i < selectedRow + 2; i++){
                        for(int j = selectedColumn - 1; j < selectedColumn + 2; j++){
                            if(i >= 0 && j >= 0 && i < row && j < column && map[i][j].equals("*")){
                                mineNumber++;
                                board[selectedRow][selectedColumn] = Integer.toString(mineNumber);
                            }else{
                                board[selectedRow][selectedColumn] = Integer.toString(mineNumber);
                            }

                        }
                    }
                    printBoard();
                    //Eğer kullanıcı oyunu kazanırsa oluşacak senaryo.
                    if(!checkWin()){
                        System.out.println("Tebrikler! Hiçbir mayına basmadan oyunu tamamladınız.");
                        printMap();
                        finish = true;
                    }
                    //Eğer kullanıcı oyunu kaybederse oluşacak senaryo.
                }else if(map[selectedRow][selectedColumn].equals("*")){
                    System.out.println(" Mayına bastınız! Kaybettiniz!");
                    printMap();
                    finish = true;
                    //Eğer kullanıcı tekrar aynı konumu girerse oluşacak senaryo.
                }else if(!board[selectedRow][selectedColumn].equals("-")){
                    System.out.println("Daha önce bu konumu verdiniz. Tekrar Girin: ");
                }

            }
            //Kullanıcı yanlış konum girerse oluşacak senaryo.
            else{
                System.out.println("Yanlış Girdiniz! Harita sınırları içerisinde bir konum seçiniz.");
            }
        }
    }

    //Kullanıcıya oyun sonunda mayın yerlerini göstermek için mayın haritasını bastırıyorum.
    public void printMap(){
        for(String[] row:map){
            for(String column: row){
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }
    //Haritayı ekrana bastırıyorm.
    public void printBoard(){
        for(String[] row:board){
            for(String column: row){
                System.out.print(column + " ");
            }
            System.out.println();
        }
        System.out.println("====================");
    }

    //Kullanıcı ilk girişinde mayına basmazsa orada oyunu bitiriyordu. Böyle bir metodla onu engelledim.
    boolean checkWin(){
        int emptyCell=0;
        int minedCell=0;
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board[i].length; j++){
                if(board[i][j].equals("-")){
                    emptyCell++;
                }
                if(map[i][j].equals("*")){
                    minedCell++;
                }
            }
        }
        if(emptyCell == minedCell){
            return false;
        }
        return true;
    }
}