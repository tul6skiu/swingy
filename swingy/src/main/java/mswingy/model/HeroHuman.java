package mswingy.model;

public enum HeroHuman {
    MAN(1, "MAN"),
    WOMAN(2, "WOMAN");

    private int id;
    private String name;

    HeroHuman(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static boolean containsCode(int code) {
        for (HeroHuman heroClass : HeroHuman.values()) {
            if (code == heroClass.getId())
                return true;
        }
        return false;
    }

    public static String getNameForId(int id) {
        for (HeroHuman heroClass1 : HeroHuman.values()) {
            if (heroClass1.getId() == id)
                return heroClass1.getName();
        }
        return null;
    }

    public static HeroHuman getClassForId(int id) {
        for (HeroHuman heroClass1 : HeroHuman.values()) {
            if (heroClass1.getId() == id)
                return heroClass1;
        }
        return null;
    }
}
