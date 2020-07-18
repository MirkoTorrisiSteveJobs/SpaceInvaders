public class PlayerShip {
    private int position = 4;
    public void move(int direction){
        switch (direction){
            case 1:
                this.position++;
                break;
            case -1:
                this.position--;
                break;
        }
    }
    public void shoot() throws InterruptedException {
        Shoot shoot = new Shoot(false, new int[]{this.position, 19});
        shoot.fire();

    }

    public int getPosition() {
        return this.position;
    }
}
