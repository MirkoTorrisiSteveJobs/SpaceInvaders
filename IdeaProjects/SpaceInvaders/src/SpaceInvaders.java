import java.util.ArrayList;

public class SpaceInvaders {
    private int score;
    private int level;
    private ArrayList<int[]> shieldCoords = new ArrayList<>();
    private int[][] grid = new int[20][20];
    private PlayerShip player = new PlayerShip();
    private ArrayList<EnemyShip> enemies = new ArrayList<>();
    public SpaceInvaders(){
        loadEnemies(1);
        loadShields();
        gridEnemies();
    }
    private void loadShields(){
        for (int i = 1; i < 17; i+=5) {
            shieldCoords.add(new int[]{i,18});
            shieldCoords.add(new int[]{i+1,18});
            shieldCoords.add(new int[]{i+2,18});
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
    }
    private void gridEnemies(){
        for (EnemyShip ship:enemies) {
            grid[ship.getPosition()[0]][ship.getPosition()[1]] = 1;
            }
        for (int[] shield:shieldCoords) {
            grid[shield[0]][shield[1]] = 2;
            }
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
                else{
                    result+="-";
                }
            }
        }
        return result;
    }
}
