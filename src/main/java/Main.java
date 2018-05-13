import Perceptrone.ThirdAttempt.*;
import Perceptrone.Util;

public class Main {
    public static void main(String[] args) {

        Engine engine = Util.loadConfig("/home/vladyslav/IdeaProjects/diploma/src/main/java/randomNetwork.json");

        final long startTime = System.currentTimeMillis();

        //engine.asyncExecute(4).forEach(System.out::println);
        engine.syncExecute().forEach(System.out::println);

        final long endTime = System.currentTimeMillis();

        System.out.println("Execution time " + (endTime - startTime));
        System.exit(0);
    }
}
