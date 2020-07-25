public class Shield {
    private int[] coords;
    private boolean isHit;
    public Shield(int [] coords){
        this.coords = coords;
    }
    public void hasBeenHit(){
        this.isHit = true;
    }
    public boolean isHit(){
        return this.isHit;
    }

    public int[] getCoords() {
        return coords;
    }
}
