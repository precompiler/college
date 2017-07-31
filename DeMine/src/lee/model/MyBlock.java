/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lee.model;

import javax.swing.JButton;

/**
 *
 * @author Owner
 */
public class MyBlock extends JButton{
    private int x;
    private int y;
    private boolean isMine;
    public boolean isVisited = false;
    @Override
    public void setSize(int x, int y) {
        super.setSize(5, 5);
    }
    public MyBlock(int x, int y, boolean isMine) {
        setSize(5, 5);
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
    public boolean getStatus() {
        return isMine;
    }
    public int getXIndex() {
        return x;
    }
    public int getYIndex() {
        return y;
    }
}
