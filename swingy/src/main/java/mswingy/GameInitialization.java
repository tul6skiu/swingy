package mswingy;

import mswingy.controller.BattlegroundController;
import mswingy.controller.HeroController;
import mswingy.model.*;
import mswingy.utils.ConfigGame;

import java.io.IOException;

public class GameInitialization {
    private static final int MAX_LVL = Integer.parseInt(ConfigGame.getConfig().getProperty("maxLvl"));

    public static void main(String[] args) {
        Hero hero;

        if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.out.println("Please select game mode(gui or console)");
            System.exit(0);
        }

        if (args[0].equals("console")) {
            ConfigGame.setMode(Mode.CONSOLE);
        } else {
            ConfigGame.setMode(Mode.GUI);
        }

        try {
            hero = new HeroController().selectHero();
            BattlegroundController battleground = new BattlegroundController();
            while (hero.getCurrentLvl() <= MAX_LVL) {
                battleground.generateMap(hero);
                GamingStatus result = battleground.playGame();
                if (result.equals(GamingStatus.LOOSE)) {
                    System.out.println("YOU LOOSE GAME =(");
                    System.exit(0);
                }
            }
            System.out.println("YOU WIN GAME!");
            System.exit(0);
        }  catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }


    }
}
