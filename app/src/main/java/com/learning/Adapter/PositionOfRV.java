package com.learning.Adapter;

/**
 * Package_name:   com.learning.Adapter
 * user:           Administrator
 * date:           2020/7/24
 * email:          ccie20079@126.com
 */
public class PositionOfRV {
    public int getSelected_position() {
        return selected_position;
    }

    public void setSelected_position(int selected_position) {
        this.selected_position = selected_position;
    }

    public PositionOfRV(int selected_position) {
        this.selected_position = selected_position;
    }

    private int selected_position;   //以此表示recyclerView中选中的position;
}
