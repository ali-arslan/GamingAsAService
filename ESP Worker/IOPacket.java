public class IOPacket {
    double mouseX;
    double mouseY;
    int scrollOffset;
    boolean rClick;
    boolean lClick;
    boolean mousePRessOrRel;
    int key;
    boolean pressedOrReleases;

    public IOPacket(double mouseX, double mouseY, int scrollOffset, boolean rClick, boolean lClick, boolean mousePressorRel, int key, boolean pressedOrReleases) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.scrollOffset = scrollOffset;
        this.rClick = rClick;
        this.lClick = lClick;
        this.mousePRessOrRel = mousePressorRel;
        this.key = key;
        this.pressedOrReleases = pressedOrReleases;
    }
}