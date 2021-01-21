package mswingy.model;

import mswingy.model.entity.ArtifactTypeEntity;

public enum ArtifactTypeGame {
    WEAPON(1, "WEAPON"),
    ARMOR(2, "ARMOR"),
    HELM(3, "HELM");

    private int id;
    private String name;

    ArtifactTypeGame(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArtifactTypeEntity toEntity() {
        ArtifactTypeEntity type = new ArtifactTypeEntity();
        type.setArtifactTypeId(this.getId());
        type.setName(this.getName());
        return type;
    }


}
