package mswingy.controller;

import mswingy.model.Hero;
import mswingy.model.HeroView;
import mswingy.model.Mode;
import mswingy.model.dao.DAOFactory;
import mswingy.utils.ConfigGame;
import mswingy.view.console.SelectHeroConsolePage;
import mswingy.view.swing.SelectHeroSwingPage;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeroController {
    private List<Hero> heroes;
    private HeroView view;

    public Hero selectHero() throws IOException, InterruptedException {
        heroes = DAOFactory.getHeroDAO().getAll();
        view.welcome();
        Hero hero;
        if (heroes != null && heroes.size() > 0) {
            if (view.booleanQuestion("You can choose old character. Do it?(y/n)", "Please write y or n")) {
                view.showAllHeroes(heroes);
                hero = view.selectHero(heroes);
            } else {
                hero = view.createHero();
                DAOFactory.getHeroDAO().save(hero);
            }
        } else {
            hero = view.createHero();
            DAOFactory.getHeroDAO().save(hero);
        }
        return hero;
    }

    public HeroController() {
        this.heroes = null;
        this.view = ConfigGame.getMode().equals(Mode.CONSOLE) ? new SelectHeroConsolePage(this) : new SelectHeroSwingPage(this);
    }

    public List<String> validateHero(Hero.HeroBuilder builder) {
        ArrayList<String> result = new ArrayList<>();
        if (DAOFactory.getHeroDAO().getForName(builder.getCharacter().getName()) != null) {
            result.add("This name already exist!");
        }
        for (ConstraintViolation<Hero> error : builder.validate()) {
            result.add(error.getMessage());
        }
        return result;
    }
}
