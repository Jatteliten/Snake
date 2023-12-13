public class WallGridSectionFactory implements GridSectionFactory{
    @Override
    public GridSection createGridSection(){
        return new GridSection(false,false,true,false);
    }
}
