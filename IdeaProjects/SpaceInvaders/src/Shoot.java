

public class Shoot extends Thread{
    private final int[] coords;
    private boolean enemy;
    private boolean hitSomething;
    public Shoot(boolean enemy, int[] coords){
        this.enemy = enemy;
        this.coords = coords;
    }
    /**
     * Metodo run della classe Shoot per avviare il Tread della classe. In questo modo il proiettile fluirà all'interno del pannello di gioco fin quanto non finirà fuori dall'arrau in cui esso è contenuto.
     * @return void
     */
    @Override
    public void run() {
        while (this.coords[1] >0 && this.coords[1] <20) {
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
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.coords[1]++;
            }
        }
    }
    /**
     * Metodo per ottenere se il colpo proviene da un nemico
     * @return bool
     */
    public boolean isEnemy() {
        return enemy;
    }
    /**
     * Metodo per ottenere le coordinate del proiettile.
     * @return int[], coordinate proeittile
     */
    public int[] getCoords() {
        return coords;
    }
    /**
     * Metodo per capire se il proiettile ha colpito qualcosa.
     * @return bool
     */
    public boolean isHitSomething() {
        return hitSomething;
    }
    /**
     * Metodo set, per settare true, che il proeittile ha colpito qualcosa
     * @return void
     */
    public void setHitSomething() {
        this.hitSomething = true;
    }
}
