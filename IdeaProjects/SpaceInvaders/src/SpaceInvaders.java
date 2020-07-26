import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class SpaceInvaders extends Thread{
    private int score;
    private int level = 1;
    private String playerName;
    private boolean gameOver;
    static ArrayList<Shoot> shootsContainer = new ArrayList<>();
    private ArrayList<Shield> shieldCoords = new ArrayList<>();
    private ArrayList<EnemyShip> enemies = new ArrayList<>();
    private PlayerShip player = new PlayerShip();
    public SpaceInvaders(String playerName){
        loadShields();
        loadEnemies(1);
        this.player.start();
        this.playerName = playerName;
    }

    protected void makeOneFrame(){
        checkGameOver();
        checkWin();
        checkShoots();
        removeShots();
        randomEnemyShot();
    }
    private void randomEnemyShot(){
        for(EnemyShip enemyShip:enemies){
            if(Math.random() <0.00001*this.level  && !enemyShip.isHit()){
                Shoot shoot = new Shoot(true, enemyShip.getPosition());
                SpaceInvaders.shootsContainer.add(shoot);
                shoot.start();
            }
        }
    }
    public void playerShot(){
        Shoot shoot = new Shoot(false, this.player.getPosition());
        SpaceInvaders.shootsContainer.add(shoot);
        shoot.start();
    }
    private void checkWin(){
        int count = 0;
        for(EnemyShip ship:enemies){
            if(ship.isHit()){
                count++;
            }
        }
        if(count == enemies.size() || score>level*300){
            this.level++;
            loadEnemies(this.level);
        }
    }
    private void checkGameOver(){
        if(!player.isInGame()){
            gameOver=true;
        }
        for(EnemyShip ship:enemies){
            if(ship.isLanded() && !ship.isHit()){
                gameOver = true;
            }
        }
    }
    private void loadShields(){
        for (int i = 1; i < 17; i+=5) {
            shieldCoords.add(new Shield(new int[]{i,18}));
            shieldCoords.add(new Shield(new int[]{i+1,18}));
            shieldCoords.add(new Shield(new int[]{i+2,18}));
        }
    }

    private void checkShoots(){
        for(Shoot shoot: shootsContainer){
            if(shoot.isEnemy()) {
                if (shoot.getCoords()[0] == player.getPosition()[0] && shoot.getCoords()[1] == player.getPosition()[1]) {
                    player.die();
                    shoot.setHitSomething();
                }
            }
            else{
                Iterator<EnemyShip> iter = enemies.iterator();

                while (iter.hasNext()) {
                    EnemyShip enemyShip = iter.next();
                    if(!shoot.isHitSomething() && shoot.getCoords()[0] == enemyShip.getPosition()[0] && shoot.getCoords()[1] == enemyShip.getPosition()[1] && !enemyShip.isHit()) {
                        enemyShip.hasBeenHit();
                        this.score+=10;
                        MediaMaker.playExplosion();
                        shoot.setHitSomething();
                    }
                }

            }
            Iterator<Shield> iter = shieldCoords.iterator();
            while (iter.hasNext()) {
                Shield shield = iter.next();
                if(!shield.isHit() && shoot.getCoords()[0] == shield.getCoords()[0] && shoot.getCoords()[1] == shield.getCoords()[1]) {
                    shield.hasBeenHit();
                    this.score-=10;
                    MediaMaker.playExplosion();
                    shoot.setHitSomething();
                }
            }
        }
    }

    private void removeShots(){
        Iterator<Shoot> iter = shootsContainer.iterator();

        while (iter.hasNext()) {
            Shoot shoot = iter.next();
            if (shoot.getCoords()[1] <2 || shoot.getCoords()[1] >19 )
                iter.remove();
        }
    }

    private void loadEnemies(int level){
        for(int i = 1; i < level+5;i++) {
            for (int j = 5; j < 15; j++) {
                EnemyShip ship = new EnemyShip(ThreadLocalRandom.current().nextInt(1, 3 + 1), new int[]{j, i});
                ship.start();
                enemies.add(ship);
            }
        }
    }

    public PlayerShip getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Shield> getShieldCoords() {
        return shieldCoords;
    }

    public ArrayList<EnemyShip> getEnemies() {
        return enemies;
    }

    public ArrayList<Shoot> getShoots() {
        return shootsContainer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
