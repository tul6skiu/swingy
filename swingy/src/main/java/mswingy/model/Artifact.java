package mswingy.model;

public class Artifact {
    private ArtifactTypeGame type;
    private int power;
    private int id;

    public Artifact(ArtifactTypeGame type, int power) {
        this.type = type;
        this.power = power;
    }

    public Artifact(ArtifactTypeGame type, int power, int id) {
        this.type = type;
        this.power = power;
        this.id = id;
    }

    public ArtifactTypeGame getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getId() {
        return id;
    }

}
