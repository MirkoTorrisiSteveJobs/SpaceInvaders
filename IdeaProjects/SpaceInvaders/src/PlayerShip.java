public class PlayerShip extends Thread{
    private int position = 4;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean inGame = true;
    private boolean dead;
    private int lives = 100;
    public void move(int direction){
        switch (direction){
            case 1:
                setMoveLeft(true);
                break;
            case -1:
                setmoveRight(true);
                break;
        }
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveRight = false;
        this.moveLeft = moveLeft;
    }
    public void setmoveRight(boolean moveRight) {
        this.moveLeft = false;
        this.moveRight = moveRight;
    }

    public void run(){
        try {
            while (inGame) {
                checkGameOver();
                sleep(80);
                if (moveLeft && this.position>0) {
                    this.position--;
                } else if (moveRight&& this.position<19) {
                    this.position++;
                }
                setDead(false);
            }
        }
        catch (InterruptedException e) {
        e.printStackTrace();
        }
    }
    public void die(){
        setDead(true);
        this.lives--;
    }
    private void checkGameOver(){
        if(this.lives <= 0){
            inGame = false;
        }
    }
    public void setDead(boolean dead){
        this.dead = dead;
    }
    public int getLives() {
        return lives;
    }

    public boolean isDead() {
        return dead;
    }

    public int[] getPosition() {
        return new int[]{this.position, 19};
    }

    public boolean isInGame() {
        return inGame;
    }
}
