import java.util.ArrayList;

public class GridSectionFactory {

    /**
     * Creates new GridSections for the Frame class to handle.
     * If the current GridSection position in the grid is on the border of the grid, create a wall.
     * If the current GridSection position in the grid is in the middle, create a snake head.
     * If it is one to the left of the snake head on the X-axis, create a snake body.
     * Else create an empty GridSection.
     * @param i current position on the Y-axis
     * @param j current position on the X-axis
     * @param gridLength The length of both the Y and the X axis.
     * @param snakeParts list of body parts for the snake that is not the head.
     * @return a modified GridSection depending on where on the grid it is being placed.
     */
    public GridSection getGridSection(int i, int j, int gridLength, ArrayList<GridSection> snakeParts){
        if(i == 0 || i == gridLength - 1 || j == 0 || j == gridLength - 1){
            return new GridSection(false, false, true, false);
        }else if(i == gridLength/2 && j == gridLength/2 - 1){
            GridSection tempGrid = new GridSection(false, true, false, false);
            snakeParts.add(tempGrid);
            return tempGrid;
        }else if(i == gridLength/2 && j == gridLength/2){
            return new GridSection(true, false, false, false);
        }else{
            return new GridSection(false, false, false, false);
        }
    }
}
