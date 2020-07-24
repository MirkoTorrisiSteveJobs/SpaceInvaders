import java.util.ArrayList;

public class Shoot extends Thread{
    private int[] coords;
    private boolean enemy;
    private boolean hitSomething;
    public Shoot(boolean enemy, int[] coords){
        this.enemy = enemy;
        this.coords = coords;
    }

    @Override
    public void run() {
        while (this.coords[1] >-1 && this.coords[1] <21) {
            try {
                sleep(160);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!enemy) {
                this.coords[1]--;
            }
            else {
                this.coords[1]++;
            }
        }
    }

    public boolean isEnemy() {
        return enemy;
    }

    public int[] getCoords() {
        return coords;
    }

    public boolean isHitSomething() {
        return hitSomething;
    }

    public void setHitSomething() {
        this.hitSomething = true;
    }
}
