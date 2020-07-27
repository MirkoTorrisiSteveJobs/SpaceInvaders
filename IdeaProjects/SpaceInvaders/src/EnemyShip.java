public class EnemyShip extends Thread {
    private int type;
    private int[] position;
    private boolean hit;
    private boolean firsTime = true;
    private boolean hasLanded;
    public EnemyShip(int type, int[] position){
        this.type = type;
        this.position = position;
    }
    /**
     * Metodo run per avviare il Thread della classe EnemyShip. In questo modo le navicelle si muoveranno in contemporanea al movimento del player
     */
    @Override
    public void run() {
        try {
            if (firsTime) {
                for (int i = 0; i < 5; i++) {
                    this.position[0]--;
                    sleep(320);
                }
                firsTime = false;
            }
            while(!hasLanded && !hit) {
                checkIfLanded();
                this.position[1]++;
                for (int i = 0; i < 10; i++) {
                    this.position[0]++;
                    sleep(320);
                }
                this.position[1]++;
                for (int i = 0; i < 10; i++) {
                    this.position[0]--;
                    sleep(320);
                }

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Funzione per controllare se la navicella nemica tocca il suolo(Quindi finisce il suo campo di gioco).
     * @return void
     */
    private void checkIfLanded(){
        if(this.position[1] == 19){
            hasLanded = true;
        }
    }
    /**
     * Funzione per controllare se la navicella nemica è atterrata.
     * @return bool
     */
    public boolean isLanded() {
        return hasLanded;
    }
    /**
     * Funzione get per ottenere la posizione della navicella nemica.
     * @return int[], indice dell'array.
     */
    public int[] getPosition() {
        return position;
    }
    /**
     * Metodo get per ottenere il tipo di navicella nemica.
     * @return int, indica il tipo di navicella
     */
    public int getType() {
        return type;
    }
    /**
     * Metodo per controllare se la navicella è  colpita.
     * @return bool
     */
    public boolean isHit() {
        return hit;
    }
    /**
     * Metodo per definire che la navicella è stata colpita
     */
    public void hasBeenHit() {
        this.hit = true;
    }
}
