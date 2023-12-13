public class AppleGridSectionFactory implements GridSectionFactory{
    @Override
    public GridSection createGridSection(){
        return new GridSection(false,false,false,true);
    }
}
