

public class Shoot extends Thread{
    private final int[] coords;
    private boolean enemy;
    private boolean hitSomething;
    public Shoot(boolean enemy, int[] coords){
        this.enemy = enemy;
        this.coords = coords;
    }

    @Override
    public void run() {
        while (this.coords[1] >-1 && this.coords[1] <21) {
            if(!enemy) {
                try {
                    sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.coords[1]--;
            }
            else {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
