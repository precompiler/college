/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lee.model;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Owner
 */
public class RandomMineGenerator {
    private int mineNum;
    private int rowNum;
    private int colNum;
    private MyBlock[][] mineRect;
    private Random random;
    private Vector<Point> mineList;
    public RandomMineGenerator(int mineNum, int rowNum, int colNum) {
        this.mineNum = mineNum;
        this.rowNum = rowNum;
        this.colNum = colNum;
        random = new Random();
        mineList = new Vector<Point> ();
        mineRect = new MyBlock[rowNum][colNum];
        for(int i = 0; i < rowNum; i++)
            for(int j = 0; j < colNum; j++)
                mineRect[i][j] = new MyBlock(i, j, false);
    }
    
    public MyBlock[][] getMineRect() {
        int c = 1;
        while(c <= mineNum) {
            int x = random.nextInt(rowNum);
            int y = random.nextInt(colNum);
            if(check(x, y)) {
                c++;
                mineList.add(new Point(x, y));
                mineRect[x][y] = new MyBlock(x, y, true);
            }
        }
        return mineRect;
    }
    private boolean check(int x, int y) {
        for(int i = 0; i < mineList.size(); i++) {
            if( (x == mineList.get(i).x) && (y == mineList.get(i).y))
                return false;
        }
        return true;
    }
}
