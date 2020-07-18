public class EnemyShip {
    private String type;
    private int[] position;
    public EnemyShip(String type, int[] position){
        this.type = type;
        this.position = position;
    }

    public int[] getPosition() {
        return position;
    }
}
