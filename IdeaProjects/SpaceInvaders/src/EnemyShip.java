public class EnemyShip extends Thread {
    private String type;
    private int[] position;
    private boolean hit;
    private boolean firsTime = true;
    private boolean hasLanded;
    public EnemyShip(String type, int[] position){
        this.type = type;
        this.position = position;
    }

    @Override
    public void run() {
        try {
            if (firsTime) {
                for (int i = 0; i < 5; i++) {
                    this.position[0]--;
                    sleep(800);
                }
                firsTime = false;
            }
            while(!hasLanded) {
                checkIfLanded();
                this.position[1]++;
                for (int i = 0; i < 10; i++) {
                    this.position[0]++;
                    sleep(800);
                }
                this.position[1]++;
                for (int i = 0; i < 10; i++) {
                    this.position[0]--;
                    sleep(800);
                }

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void checkIfLanded(){
        if(this.position[1] == 19){
            hasLanded = true;
        }
    }

    public boolean isLanded() {
        return hasLanded;
    }

    public int[] getPosition() {
        return position;
    }

    public boolean isHit() {
        return hit;
    }
    public void hasBeenHit() {
        this.hit = true;
    }
}
