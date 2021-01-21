package mswingy.model.entity;

import mswingy.model.ArtifactTypeGame;

import javax.persistence.*;

@Entity
@Table(name = "T_ARTIFACT_TYPE", schema = "PUBLICE")
public class ArtifactTypeEntity {
	@Id
	@Column(name = "T_ARTIFACT_TYPE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artifactTypeId;
	@Column(name = "NAME")
	private String name;

	public int getArtifactTypeId() {
		return artifactTypeId;
	}

	public void setArtifactTypeId(int artifactTypeId) {
		this.artifactTypeId = artifactTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ArtifactType: {" +
				"artifactTypeId: " + artifactTypeId +
				", name: " + name +
				"}";
	}

	public ArtifactTypeGame toBean() {
		for (ArtifactTypeGame art : ArtifactTypeGame.values()) {
			if (art.getName().equals(name))
				return art;
		}
		return null;
	}
}
