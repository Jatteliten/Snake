import java.util.ArrayList;

public class GridSectionFactory {

    public GridSection getGridSection(int i, int j, int length, ArrayList<GridSection> snakeParts){
        if(i == 0 || i == length - 1 || j == 0 || j == length - 1){
            return new GridSection(false, false, true, false);
        }else if(i == length/2 && j == length/2 - 1){
            GridSection tempGrid = new GridSection(false, true, false, false);
            snakeParts.add(tempGrid);
            return tempGrid;
        }else if(i == length/2 && j == length/2){
            return new GridSection(true, false, false, false);
        }else{
            return new GridSection(false, false, false, false);
        }
    }
}
