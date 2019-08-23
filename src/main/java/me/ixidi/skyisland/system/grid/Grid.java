package me.ixidi.skyisland.system.grid;

public interface Grid {

    GridPosition takeFirstFree();

    void setFree(GridPosition position);

}
