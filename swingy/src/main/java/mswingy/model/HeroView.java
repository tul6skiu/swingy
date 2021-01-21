package mswingy.model;

import java.io.IOException;
import java.util.List;

public interface HeroView  extends View{
    void welcome() throws IOException;
    void showAllHeroes(List<Hero> heroes);
    boolean booleanQuestion(String text, String errorText) throws IOException;
    Hero selectHero(List<Hero> heroes) throws IOException, InterruptedException;
    Hero createHero() throws IOException, InterruptedException;
}
