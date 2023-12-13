public class DefaultGridSectionFactory implements GridSectionFactory {
    @Override
    public GridSection createGridSection() {
        return new GridSection(false, false, false, false);
    }
}