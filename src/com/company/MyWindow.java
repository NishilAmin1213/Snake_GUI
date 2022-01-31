package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MyWindow extends JFrame {

    /*
    Snake has 2 main elements, the SQUARE/TARGET, and the snake
    The snake itself moves in the direction of the arrow pressed
    it is comprised of a number of cubes
    after each level a cube is added to the snake
    if 2 squares of the snake are on the same square, the user loses
     */

    //store 2d array to store the snake and the target
    int[][] dat = new int[25][25];
    public int dir = 0b11;
    int x = 12;
    int y = 12;
    int len = 3;
    int lev = 0;
    BufferedImage fruit;




    private void addFruit(){
        Random r = new Random();
        int rx = r.nextInt(25);
        int ry = r.nextInt(25);
        if(dat[rx][ry] > 0) addFruit();
        else dat[rx][ry] = -1;
    }


    public MyWindow(){
        dat[x][y] = len;
        this.setTitle("Snake Window");
        this.setSize(500,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        addFruit();

        //load image to display
        fruit = null;
        try{
            fruit = ImageIO.read(new File("summer.png"));
            System.out.println(fruit.getWidth() + " " + fruit.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }


        JPanel canvas = new JPanel() {

            @Override
            public void paint(Graphics g) {
                g.clearRect(0, 0, getWidth(), getHeight()); // clears previous square

                int gx = getWidth()/25, gy = getHeight()/25, ox = (getWidth()%25)/2, oy = (getHeight()%25)/2;


                for(int i=0;i<25;i++){
                    for(int j = 0;j<25;j++){
                        if(dat[i][j] >0){
                            g.setColor(Color.BLUE);
                            g.fillRect(i*gx+ox, j*gy+oy, gx, gy);
                            dat[i][j]--;
                        }else if(dat[i][j] == -1){
                            g.setColor(Color.RED);
                            g.drawImage(fruit, i*gx + ox, j*gy + oy, null);
                            //g.fillOval(i*gx + ox, j*gy+oy, gx, gy);
                        }
                    }
                }
            }
        };
        content.add(canvas, BorderLayout.CENTER);
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) { //up = 00, down = 01, left = 10, right = 11
                System.out.println(e.getKeyCode()); // up:38 down:40 left:37 right:39
                if(e.getKeyCode() == 38){
                    //change direction to up
                    dir = 0b00;
                }else if(e.getKeyCode() == 40){
                    //change direction to down
                    dir = 0b01;
                }else if(e.getKeyCode() == 37){
                    //change direction to left
                    dir = 0b10;
                }else if(e.getKeyCode() == 39){
                    //change direction to right
                    dir = 0b11;
                }
            }
        });
        new java.util.Timer().scheduleAtFixedRate(new java.util.TimerTask() {
            public void run() {
                if(dir == 0b00){
                    y--;
                }else if(dir == 0b01){
                    y++;
                }else if(dir == 0b10){
                    x--;
                }else if(dir == 0b11){
                    x++;
                }
                x = x % 25;
                y = y % 25;
                if(x < 0){
                    x = 24;
                }
                if(y < 0){
                    y = 24;
                }
                if(dat[x][y] > 0) {
                    //collision
                    //GAME END
                    System.exit(0);
                }else if(dat[x][y] == -1){
                    //eaten fruit
                    len++;
                    addFruit();
                    lev++;
                }

                dat[x][y] = len;
                canvas.repaint();
            }
        }, 0, 500);

        this.setVisible(true);
    }

}


//in haskell
//shunting yard algorithm, stacks in queues
//dijkstras algorithm in haskell, using adj list or adj matrix


