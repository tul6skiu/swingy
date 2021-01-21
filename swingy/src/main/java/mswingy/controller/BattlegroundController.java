package mswingy.controller;

import mswingy.model.*;
import mswingy.model.dao.DAOFactory;
import mswingy.utils.ConfigGame;
import mswingy.view.console.ConsoleBattlePage;
import mswingy.view.swing.BattlePage;
import mswingy.view.console.BattleView;
import mswingy.view.console.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static mswingy.model.GeneratorFactory.*;
import static mswingy.view.console.Button.*;

public class BattlegroundController {
    private Map<Coordinate, GameCharacter> characters;
    private Coordinate heroCoordinates;
    private int mapSize;
    private static int VILLAIN_AMOUNT;
    private BattleView view;
    private GamingStatus status;
    private Hero.HeroBuilder heroBuilder;

    public GamingStatus getStatus() {
        return status;
    }

    /**
     * Генерация карты, в зависимости от уровня игрока
     */
    public void generateMap(Hero hero) /*throws GenerateMapException*/ {
        status = GamingStatus.PLAY;
        characters = null;
        characters = new HashMap<>();
        mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
        heroCoordinates = new Coordinate(mapSize / 2, mapSize / 2);
        characters.put(heroCoordinates, hero);
        for (int i = 0; i < VILLAIN_AMOUNT; i++) {
            characters.put(generateNewCoordinate(mapSize, characters),
                    generateVillain(hero.getCurrentLvl()));
        }
    }

    /**
     * Цикл игры
     */
    public GamingStatus playGame() throws IOException, InterruptedException {
        if (ConfigGame.getMode().equals(Mode.GUI)) {
            view = new BattlePage(characters, mapSize, this);
        } else {
            view = new ConsoleBattlePage(characters, mapSize, this);
        }
        view.printMap();
        while (status.equals(GamingStatus.PLAY)) {
            Thread.sleep(100);
        }
        view.destroy();
        if (status.equals(GamingStatus.LOOSE))
            return GamingStatus.LOOSE;
        else
            return GamingStatus.WIN;
    }

    public void pressButton(Button button) throws IOException, InterruptedException {
        if (button == null)
            return;
        if (Button.isStep(button.getCode())) {
            getStep(button);
        }
        if (button.equals(ESC)) {
            Hero hero = (Hero) characters.get(heroCoordinates);
            DAOFactory.getHeroDAO().update(hero);
            System.exit(0);
        }
    }

    private void getStep(Button button) throws IOException, InterruptedException {
        Coordinate coordinate;
        if (button.equals(LEFT)) {
            if (heroCoordinates.getX() == 0) {
                status = GamingStatus.WIN;
            } else {
                coordinate = new Coordinate(heroCoordinates.getX() - 1, heroCoordinates.getY());
                if (characters.containsKey(coordinate)) {
                    battle(coordinate);
                } else {
                    replaceCoordinates(coordinate);
                }
            }

        } else if (button.equals(RIGHT)) {
            if (heroCoordinates.getX() == mapSize - 1) {
                status = GamingStatus.WIN;
            } else {
                coordinate = new Coordinate(heroCoordinates.getX() + 1, heroCoordinates.getY());
                if (characters.containsKey(coordinate)) {
                    battle(coordinate);
                } else {
                    replaceCoordinates(coordinate);
                }
            }

        } else if (button.equals(UP)) {
            if (heroCoordinates.getY() == 0) {
                status = GamingStatus.WIN;
            } else {
                coordinate = new Coordinate(heroCoordinates.getX(), heroCoordinates.getY() - 1);
                if (characters.containsKey(coordinate)) {
                    battle(coordinate);
                } else {
                    replaceCoordinates(coordinate);
                }
            }

        } else if (button.equals(DOWN)) {
            if (heroCoordinates.getY() == mapSize - 1) {
                status = GamingStatus.WIN;
            } else {
                coordinate = new Coordinate(heroCoordinates.getX(), heroCoordinates.getY() + 1);
                if (characters.containsKey(coordinate)) {
                    battle(coordinate);
                } else {
                    replaceCoordinates(coordinate);
                }
            }
        }
        if (view instanceof BattlePage)
            view.printMap();
    }

    private void replaceCoordinates(Coordinate newCoordinates) {
        GameCharacter character = characters.remove(heroCoordinates);
        heroCoordinates.setX(newCoordinates.getX());
        heroCoordinates.setY(newCoordinates.getY());
        characters.put(heroCoordinates, character);
    }

    private void battle(Coordinate villainCoordinates) throws IOException, InterruptedException {
        GameCharacter enemy = characters.get(villainCoordinates);
        if (!(enemy instanceof Villain))
            return;
        Villain villain = (Villain) enemy;
        Hero hero = (Hero) characters.get(heroCoordinates);
        int heroHp = hero.getHitPoint();
        int villainHp = villain.getHitPoint();
        boolean heroStep = true;
        int counter = 0;
        while (heroHp > 0 && villainHp > 0 && counter < 50) {
            if (heroStep) {
                heroStep = false;
                villainHp = villainHp - (hero.getAttack() - villain.getDefence());
            } else {
                heroStep = true;
                heroHp = heroHp - (villain.getAttack() - hero.getDefence());
            }
            counter++;
        }
        if (heroHp <= 0) {
            status = GamingStatus.LOOSE;
        } else {
            if (view.fightChoice()) {
                characters.remove(villainCoordinates);
                replaceCoordinates(villainCoordinates);
                hero.updateExp();
                if (hero.getCurrentLvl() > ConfigGame.getMaxLvl()) {
                    System.out.println("YOU WIN GAME!");
                    System.exit(0);
                }
                heroBuilder.setCharacter(hero);
                Artifact artifact = generateArtifact(hero.getCurrentLvl());
                if (artifact != null && view.artifactChoice(artifact)) {
                    heroBuilder.takeArtifact(artifact);
                }
            }
        }
    }

    public BattlegroundController() {
        VILLAIN_AMOUNT = Integer.parseInt(ConfigGame.getConfig().getProperty("villain.amount"));
        heroBuilder = new Hero.HeroBuilder(null);
    }
}
