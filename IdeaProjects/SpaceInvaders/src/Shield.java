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
    /**
     * Metodo per ottere le coordinate del posizionamento dello scudo.
     * @return int[], indice dell'array.
     */
    public int[] getCoords() {
        return coords;
    }
}
