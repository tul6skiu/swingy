package mswingy.view.console;

import mswingy.model.Artifact;
import mswingy.model.View;

import java.io.IOException;

public interface BattleView extends View {
    void printMap() throws IOException;

    boolean fightChoice() throws IOException, InterruptedException;

    boolean artifactChoice(Artifact artifact) throws IOException, InterruptedException;

}
