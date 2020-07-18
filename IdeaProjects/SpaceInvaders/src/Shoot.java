public class Shoot extends Thread{
    private int[] coords;
    private boolean enemy;
    private boolean hitSomething;
    public Shoot(boolean enemy, int[] coords){
        this.enemy = enemy;
        this.coords = coords;
    }

    public int[] getCoords() {
        return coords;
    }
    public void fire() throws InterruptedException {
        while (this.coords[1] >0 || !hitSomething) {
                sleep(100);
                this.coords[1]--;
            }
        }
    }
    public void hasHitSomething() {
        this.hitSomething = true;
    }
}
