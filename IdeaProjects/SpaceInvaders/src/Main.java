import java.util.Scanner;

public class Main {
    public static void main(String[]args){
        SpaceInvaders gsame = new SpaceInvaders();
        gsame.start();
        while (!gsame.isGameOver()) {
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();
            if (answer.equalsIgnoreCase("4")) {
                gsame.getPlayer().move(1);
            } else if (answer.equalsIgnoreCase("6")) {
                gsame.getPlayer().move(-1);
            } else if (answer.equalsIgnoreCase("8")) {
                gsame.playerShot();
            }
        }
    }
}
