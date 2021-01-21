package mswingy.model;

import mswingy.model.entity.ArtifactDao;
import mswingy.model.entity.CharacterEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CharacterEntityConverter {


    public static Character heroToEntity(Hero hero) {
        Character character = new Character();
        character.setName(hero.getName());
        int attack = 0, def = 0, hp = 0;
        //artifacts
        if (hero.getArtifacts() != null) {
            Set<ArtifactDao> artifacts = new HashSet<>();
            for (Map.Entry<ArtifactTypeGame, Artifact> entry : hero.getArtifacts().entrySet()) {

                ArtifactDao newArt = new ArtifactDao();
                newArt.setArtifactType(entry.getValue().getType().toEntity());
                newArt.setPower(entry.getValue().getPower());
                newArt.setCharacter(character);
                newArt.setArtifactId(entry.getValue().getId());
                artifacts.add(newArt);
                if (entry.getValue().getType().equals(ArtifactTypeGame.ARMOR))
                    def += entry.getValue().getPower();
                else if (entry.getValue().getType().equals(ArtifactTypeGame.HELM))
                    hp += entry.getValue().getPower();
                else if (entry.getValue().getType().equals(ArtifactTypeGame.WEAPON))
                    attack += entry.getValue().getPower();
            }
            character.setArtifacts(artifacts);
        }
        character.setAttack(hero.getAttack() - attack);
        character.setDefence(hero.getDefence() - def);
        character.setHitPoint(hero.getHitPoint() - hp);
        character.setLevel(hero.getCurrentLvl());
        character.setExp(hero.getExp());
        //class
        CharacterClass chClass = new CharacterClass();
        chClass.setCharacterClassId(hero.getHuman().getId());
        chClass.setClassName(HeroHuman.getNameForId(hero.getHuman().getId()));
        character.setCharacterClass(chClass);
        return character;
    }

    public static Hero entityToHero(Character character) {
        Hero.HeroBuilder builder = new Hero.HeroBuilder(
                new Hero(HeroHuman.getClassForId(character.getCharacterClass().getCharacterClassId())));
        builder.setName(character.getName()).
                setLevel(character.getLevel()).
                setAttack(character.getAttack()).
                setDefence(character.getDefence()).
                setHitPoint(character.getHitPoint()).
                setExp(character.getExp());
        //artifacts
        if (character.getArtifacts() != null) {
            Map<ArtifactTypeGame, Artifact> artifactMap = new HashMap<>();
            for (ArtifactDao art : character.getArtifacts()) {
                artifactMap.put(art.getArtifactTypeEntity().toBean(),
                        new Artifact(art.getArtifactTypeEntity().toBean(), art.getPower(), art.getArtifactId()));
            }
            builder.setArtifacts(artifactMap);
        }
        return builder.getCharacter();
    }
}
