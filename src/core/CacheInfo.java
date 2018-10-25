package core;

public class CacheInfo {
    private String id;
    private float weight = 1;
    private float popularity = 1;
    private int hittime = 1;
    private int lasthit = 1;
    private int hitgap = 1;


    public CacheInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getHittime() {
        return hittime;
    }

    public void setHittime(int hittime) {
        this.hittime = hittime;
    }

    public int getLasthit() {
        return lasthit;
    }

    public void setLasthit(int lasthit) {
        this.lasthit = lasthit;
    }

    public int getHitgap() {
        return hitgap;
    }

    public void setHitgap(int hitgap) {
        this.hitgap = hitgap;
    }
}
