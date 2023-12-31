import java.util.Random;
import java.util.Scanner;

public class MineSweeper{
    int row,column,mineNumber;
    String[][] map;
    String[][] board;
    boolean finish = false;

    Scanner input = new Scanner(System.in);
    Random randomMineNumber = new Random();

    //Oyunu çalıştıran metot.(Temel Fonksiyonlar 2.)
    public void run(){
        System.out.println("Mayın Tarlası Oyununa Hoş Geldiniz!");

        //Kullanıcıdan oluşacak haritanın satır ve sütun sayısını aldım.(Temel Fonksiyonlar 3.)
        System.out.print("Satır Sayısını Giriniz:  ");
        row = input.nextInt();
        System.out.print("Sütun Sayısını Giriniz: ");
        column = input.nextInt();

        //Mayınları eleman sayısının çeyreği olacağı şekilde tanımladım.(Temel Fonksiyonlar 4. Uygun sayıda mayın)
        mineNumber = (row*column) / 4;

        //Mayınların konumlarını tutacak bir dizi ve kullanıcıya sunulacak bir dizi oluşturduk.
        map = new String[row][column];
        board = new String[row][column];

        for(int i=0; i<row; i++){
            for(int j=0; j<column; j++){
                board[i][j] = "-";
                map[i][j] = "-";
            }
        }
        //Mayınların haritaya rastgele yerleşmesi ve aynı noktaya tekrar yerleşmemesi için while döngüsü ve if kullandım.(Temel Fonksiyonlar 4.)
        int count = 0;
        while(count != mineNumber){
            int rowMineNumber = randomMineNumber.nextInt(row);
            int columnMineNumber = randomMineNumber.nextInt(column);

            if(map[rowMineNumber][columnMineNumber].equals("-")){
                map[rowMineNumber][columnMineNumber] = "*";
                count++;
            }
        }
        printMap();
        System.out.println("====================");

        printBoard();

        playCheck();
    }

    ////Oyunu kontrol eden metot.(Temel Fonksiyonlar 2.)
    public void playCheck(){
        System.out.println("Oyun Başladı!");
        while(!finish){
            //Kullanıcıdan seçeceği nokta için satır ve sütun sayısını istedim.(Temel Fonksiyonlar 5.)
            System.out.print("Satır Sayısı: ");
            int selectedRow = input.nextInt();
            System.out.print("Sütun Sayısı: ");
            int selectedColumn = input.nextInt();

            int mineNumber=0;
            //Kullanıcının girdiği konumda etrafındaki mayın sayısını yazdırdım.(Temel Fonksiyonlar 8.)
            if(selectedRow < row && selectedColumn < column){
                if(map[selectedRow][selectedColumn].equals("-") && board[selectedRow][selectedColumn].equals("-")){
                    for(int i = selectedRow - 1; i <= selectedRow + 1; i++){
                        for(int j = selectedColumn - 1; j <= selectedColumn + 1; j++){
                            if(i >= 0 && j >= 0 && i < row && j < column && map[i][j].equals("*")){
                                mineNumber++;
                                //Dizilerim string tipinde olduğu için haritaya yazdırabilmek için type casting kullandım.
                                board[selectedRow][selectedColumn] = Integer.toString(mineNumber);
                            }
                            else{
                                board[selectedRow][selectedColumn] = Integer.toString(mineNumber);
                            }
                        }
                    }
                    //(Temel Fonksiyonlar 7.) Oyun alanı her seferinde güncelleniyor.
                    printBoard();

                    //Eğer kullanıcı oyunu kazanırsa oluşacak senaryo.(Temel Fonksiyonlar 10.-11)
                    if(checkWin()){
                        System.out.println("Tebrikler! Hiçbir mayına basmadan oyunu tamamladınız.");
                        //Kullanıcının mayınların nerede olduğunu görmesi için oyun sonunda mayın haritasını bastırıyorum.
                        printMap();
                        finish = true;
                    }
                }

                //Eğer kullanıcı oyunu kaybederse oluşacak senaryo.(Temel Fonksiyonlar 9.-11.)
                else if(map[selectedRow][selectedColumn].equals("*")){
                    System.out.println("Mayına bastınız! Kaybettiniz!");
                    //Kullanıcının mayınların nerede olduğunu görmesi için oyun sonunda mayın haritasını bastırıyorum.
                    printMap();
                    finish = true;
                }

                //Eğer kullanıcı tekrar aynı konumu girerse oluşacak senaryo.
                else if(!board[selectedRow][selectedColumn].equals("-")){
                    System.out.println("Daha önce bu konumu verdiniz. Tekrar Giriniz. ");
                }
            }

            //Kullanıcı yanlış konum girerse oluşacak senaryo.(Temel Fonksiyonlar 6.)
            else{
                System.out.println("Yanlış Girdiniz! Harita sınırları içerisinde bir konum seçiniz.");
            }
        }
    }

    //Mayın haritasını yazdıran metot.(Temel Fonksiyonlar 2.)
    public void printMap(){
        for(String[] row:map){
            for(String column: row){
                System.out.print(column + " ");
            }
            System.out.println();
        }
    }
    //Oyunu haritasını yazdıran metot.(Temel Fonksiyonlar 2.)
    public void printBoard(){
        for(String[] row:board){
            for(String column: row){
                System.out.print(column + " ");
            }
            System.out.println();
        }
        System.out.println("====================");
    }

    //Kullanıcının oyunu ne zaman kaybedip ne zaman kazandığının kontrolünü sağladığım metot.(Temel Fonksiyonlar 2-9-10)
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
            return true;
        }
        return false;
    }
}