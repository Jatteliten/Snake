public class SnakeHeadGridSectionFactory implements GridSectionFactory{
    @Override
    public GridSection createGridSection(){
        return new GridSection(true,false,false,false);
    }
}
