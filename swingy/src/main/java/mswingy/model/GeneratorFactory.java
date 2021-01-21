package mswingy.model;

import java.util.Map;
import java.util.Random;

public abstract class GeneratorFactory {
    private static final int MAX_ITERATIONS = 100;
    private static Random random = new Random();

    public static Coordinate generateNewCoordinate(int mapSize, Map<Coordinate, GameCharacter> characters)  {
        Coordinate result = new Coordinate();
        int i = 0;
        while (i < MAX_ITERATIONS) {
            result.setX(random.nextInt(mapSize));
            result.setY(random.nextInt(mapSize));
            if (!characters.containsKey(result))
                break;
            i++;
        }
        //todo:совя ошибка вместо вывода
        if (i == MAX_ITERATIONS) {
            System.out.println("To many iterations.");
        }
        return result;
    }

    public static Villain generateVillain(int heroLvl) {
        return new Villain(heroLvl / 2, heroLvl / 4, heroLvl * 2);
    }

    public static Artifact generateArtifact(int heroLvl) {
        ArtifactTypeGame[] types = ArtifactTypeGame.values();
        if (random.nextBoolean()) {
            return new Artifact(types[random.nextInt(3)], random.nextInt(heroLvl) + 1);
        }
        return null;
    }
}
