public class SnakeBodyGridSectionFactory implements GridSectionFactory{
    @Override
    public GridSection createGridSection(){
        return new GridSection(false,true,false,false);
    }
}
