import java.util.ArrayList;
import java.util.Iterator;

public class SpaceInvaders extends Thread{
    private int score;
    private int level;
    static ArrayList<Shoot> shootsContainer = new ArrayList<>();
    private boolean gameOver;
    private ArrayList<int[]> shieldCoords = new ArrayList<>();
    private int[][] grid = new int[20][20];
    private PlayerShip player = new PlayerShip();
    private ArrayList<EnemyShip> enemies = new ArrayList<>();
    public SpaceInvaders(){
        loadEnemies(1);
        loadShields();
        //gridElements();
        this.player.start();
        for(EnemyShip ship: enemies){
            ship.start();
        }
    }

    /*
    @Override
    public void run(){
        try {
                makeOneFrame();
                Thread.sleep(800);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    */
    protected void makeOneFrame(){
        checkGameOver();
        checkShoots();
        removeShots();
        randomEnemyShot();
        //gridElements();
    }
    private void randomEnemyShot(){
        for(EnemyShip enemyShip:enemies){
            if(Math.random() <0.000009){
                System.out.println("nemico spara");
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
    private void checkGameOver(){
        for(EnemyShip ship:enemies){
            if(ship.isLanded()){
                System.out.println("eh sono atterrati");
                gameOver = true;
            }
        }
    }
    private void loadShields(){
        for (int i = 1; i < 17; i+=5) {
            shieldCoords.add(new int[]{i,18});
            shieldCoords.add(new int[]{i+1,18});
            shieldCoords.add(new int[]{i+2,18});
        }
    }

    private void checkShoots(){
        for(Shoot shoot: shootsContainer){
            if(shoot.isEnemy()) {
                if (shoot.getCoords()[0] == player.getPosition()[0] && shoot.getCoords()[1] == player.getPosition()[1]) {
                    player.die();
                    System.out.println("TI HANNO PRESO");
                    shoot.setHitSomething();
                }
            }
            else{
                Iterator<EnemyShip> iter = enemies.iterator();

                while (iter.hasNext()) {
                    EnemyShip enemyShip = iter.next();
                    if(!shoot.isHitSomething() && shoot.getCoords()[0] == enemyShip.getPosition()[0] && shoot.getCoords()[1] == enemyShip.getPosition()[1] && !enemyShip.isHit()) {
                        enemyShip.hasBeenHit();
                        shoot.setHitSomething();
                    }
                }

            }
            Iterator<int[]> iter = shieldCoords.iterator();
            while (iter.hasNext()) {
                int[] coords = iter.next();
                if(shoot.getCoords()[0] == coords[0] && shoot.getCoords()[1] == coords[1]) {
                    iter.remove();
                    shoot.setHitSomething();
                }
            }
        }
    }
    private void loadEnemies(int level){
        switch (level){
            case 1:
                for (int j = 5; j < 15; j++) {
                        enemies.add(new EnemyShip("tipe1", new int[]{j, 1}));
                }
                for (int j = 5; j < 15; j++) {
                        enemies.add(new EnemyShip("tipe2", new int[]{j, 2}));
                }
                for (int j = 5; j < 15; j++) {
                        enemies.add(new EnemyShip("tipe2", new int[]{j, 3}));
                }
                for (int j = 5; j < 15; j++) {
                        enemies.add(new EnemyShip("tipe3", new int[]{j, 4}));
                }
                for (int j = 5; j < 15; j++) {
                        enemies.add(new EnemyShip("tipe3", new int[]{j, 5}));
                }
                break;
        }
    }/*
    private void removeEnemies(EnemyShip enemyShipToRemove){
        Iterator<EnemyShip> iter = enemies.iterator();

        while (iter.hasNext()) {
            EnemyShip enemyShip = iter.next();
            if (enemyShip.equals(enemyShipToRemove));
                iter.remove();
        }
        System.out.println("nemico rimosso");
    }*/
    private void removeShots(){
        Iterator<Shoot> iter = shootsContainer.iterator();

        while (iter.hasNext()) {
            Shoot shoot = iter.next();
            if (shoot.getCoords()[1] <2 || shoot.getCoords()[1] >19 || shoot.isHitSomething())
                iter.remove();
        }
    }/*
    private void gridElements(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
        for (EnemyShip ship:enemies) {
            grid[ship.getPosition()[0]][ship.getPosition()[1]] = 1;
            }
        for (int[] shield:shieldCoords) {
            grid[shield[0]][shield[1]] = 2;
            }
        for(Shoot shoot:shootsContainer){
            grid[shoot.getCoords()[0]][shoot.getCoords()[1]] = 3;
        }
        grid[player.getPosition()[0]][player.getPosition()[1]] = 4;

    }*/

    public PlayerShip getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<int[]> getShieldCoords() {
        return shieldCoords;
    }

    public int[][] getGrid() {
        return grid;
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

    public String toString(){
        String  result = "";
        for (int i = 0; i < grid.length; i++) {
            result+="\n";
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[j][i] == 1){
                    result+="S";
                }
                else if(grid[j][i] == 2){
                    result+="w";
                }
                else if(grid[j][i] == 3){
                    result+="|";
                }
                else if(grid[j][i] == 4){
                    result+="^";
                }
                else{
                    result+="-";
                }
            }
        }
        return result;
    }
}
