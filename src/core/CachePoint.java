package core;

public class CachePoint {
    /**
     * @param id 缓存的id
     * @param content 缓存列的内容 在实例对象时必须要有
     * @param weight 缓存列的权重
     * @param popularity 缓存列的流行度
     * @param hittime 此缓存被访问次数
     * @param lasthit 上一次访问的时间
     * @param hitgap 包的访问时差
     * @param size 包的大小
     */
    private String id;
    private String content;
    private float weight = 1;
    private float popularity = 1;
    private int hittime = 1;
    private int lasthit = 1;
    private int hitgap = 1;
    private int size = 0;

    public CachePoint(String content) {
        setContent(content);
    }

    public CachePoint(CacheInfo cacheInfo) {
        this.content = cacheInfo.getId();
        this.weight = cacheInfo.getWeight();
        this.popularity = cacheInfo.getPopularity();
        this.hittime = cacheInfo.getHittime();
        this.lasthit = cacheInfo.getLasthit();
        this.hitgap = cacheInfo.getHitgap();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getWeight() {
        return this.weight;
    }

    public float getPopularity() {
        return this.popularity;
    }

    public String getContent() {
        return this.content;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
