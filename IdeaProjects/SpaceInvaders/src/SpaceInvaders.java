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
    /**
     * Funzione per che inizializza un frame, richiama le altre funzioni che creano la logica del gioco
     */
    protected void makeOneFrame(){
        checkGameOver();
        checkWin();
        checkShoots();
        removeShots();
        randomEnemyShot();
    }
    /**
     * Funzione per far sparare in maniera random un nemico.Eseguirà un controllo tra tutti i nemici e tramite l'utilizzo del Math.Random sceglie a caso il nemico che sparerà
     * @return void
     */
    private void randomEnemyShot(){
        for(EnemyShip enemyShip:enemies){
            if(Math.random() <0.00001*this.level  && !enemyShip.isHit()){
                Shoot shoot = new Shoot(true, enemyShip.getPosition());
                SpaceInvaders.shootsContainer.add(shoot);
                shoot.start();
            }
        }
    }
    /**
     * Funzione per far sparare il player.
     */
    public void playerShot(){
        Shoot shoot = new Shoot(false, this.player.getPosition());
        SpaceInvaders.shootsContainer.add(shoot);
        shoot.start();
    }
    /**
     * Funzione per calcolare se il player ha vinto la partita.Viene effettuato un controllo sul numero di nemici uccisi.Qualora il numero di nemici uccisi è uguale alla dimensione del contenitore dei nemici oppure il punteggio è di 250, il player vince la partita
     * @return void
     */
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
    /**
     * Funzione per controllare se la partita deve finire. Se il giocatore non è in partita allora il gioco finisce.
     * @return void
     */
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
    /**
     * Funzione per caricare gli scudi. Tramite un ciclo for sono caricati gli scudi.
     * @return void
     */
    private void loadShields(){
        for (int i = 1; i < 17; i+=5) {
            shieldCoords.add(new Shield(new int[]{i,18}));
            shieldCoords.add(new Shield(new int[]{i+1,18}));
            shieldCoords.add(new Shield(new int[]{i+2,18}));
        }
    }
    /**
     * Funzione per controllare se lo sparo ha colpito il giocatore, gli scudi o i nemici. Viene effettuato un controllo all'interno dell'arrayList shootsContainer. Se lo sparo si trova nella stessa posizione del player, quest'ultimo perde una vita. Mentre  lo sparo coincide con la posizione del nemico quest'ultimo viene colpito e distrutto generando un suono.
     * @return void
     */
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
    /**
     * Funzione per rimuovere lo sparo dopo che ha superato l'intera colonna di nemici.
     * @return void
     */
    private void removeShots(){
        Iterator<Shoot> iter = shootsContainer.iterator();

        while (iter.hasNext()) {
            Shoot shoot = iter.next();
            if (shoot.getCoords()[1] <2 || shoot.getCoords()[1] >19 )
                iter.remove();
        }
    }
    /**
     * Funzione per caricare i nemici in base al livello.
     * @param level
     */
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
