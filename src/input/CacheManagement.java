package input;

import core.CacheInfo;
import core.CachePoint;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class CacheManagement {
    /**
     * @param totalHit 节点的访问次数；
     * @param cachePointList 缓存列的list
     */
    private int hit;
    private int totalHit;
    private int maxSize;
    private int currentSize;
    private List<CachePoint> cachePointlist = new ArrayList<CachePoint>();
    private List<CacheInfo> cacheList = new ArrayList<CacheInfo>();

    public CacheManagement() {
        hit = 0;
        totalHit = 0;
        maxSize = 20;
        currentSize = 10;
        init();
    }

    //流行度更新
    public void popularityCount(String content) {
        //Todo
        for (CacheInfo cacheInfo : cacheList) {
            float newP = cacheInfo.getPopularity() +
                    (float) 0.3 * (float) cacheInfo.getHittime() + cacheInfo.getWeight();
            cacheInfo.setPopularity(newP > 200 ? 50 : newP);
        }
        for (CachePoint cachePoint : cachePointlist) {
            //流行度计算公式
            //Pt+1 = Pt + 0.4 * Ht + Wi
            float newP = cachePoint.getPopularity() +
                    (float) 0.3 * (float) cachePoint.getHittime() + cachePoint.getWeight();
            cachePoint.setPopularity(newP > 200 ? 50 : newP);
        }
    }

    //权重更新
    public void weightUpdate(String content) {
        if (currentSize != 0) {
            for (CacheInfo cacheInfo : cacheList) {
                int T = totalHit - cacheInfo.getLasthit();
                if (cacheInfo.getId().equals(content)) {
                    cacheInfo.setLasthit(totalHit);
                    cacheInfo.setHitgap(totalHit - cacheInfo.getLasthit());
                    cacheInfo.setHittime(cacheInfo.getHittime() + 1);
                }
                float weight = (float) T /
                        ((float) cacheInfo.getHittime() * (float) cacheInfo.getHitgap());
                cacheInfo.setWeight(weight);
            }

            for (CachePoint cachePoint : cachePointlist) {
                //System.out.println("CacheManagement:weightUpdate " + cachePoint.getContent() + " " + cachePoint.getPopularity() + "   " + cachePoint.getWeight());
                //距上次访问后节点的访问次数
                int T = totalHit - cachePoint.getLasthit();
                //命中
                if (cachePoint.getContent().equals(content)) {
                    cachePoint.setLasthit(totalHit);
                    cachePoint.setHitgap(totalHit - cachePoint.getLasthit());
                    cachePoint.setHittime(cachePoint.getHittime() + 1);
                }
                float weight = (float) T /
                        ((float) cachePoint.getHittime() * (float) cachePoint.getHitgap());
                cachePoint.setWeight(weight);
            }
            popularityCount(content);
        }
    }

    /**
     * 使用了dropCachePoint()函数就要使用addCachePoint()函数
     * 在list未满之前使用addCachePoint()函数
     */

    //删除流行度最低的缓存
    public void dropCachePoint() {
        //todo
        Collections.sort(cachePointlist, new Comparator<CachePoint>() {
            @Override
            public int compare(CachePoint o1, CachePoint o2) {
                float i = o1.getPopularity() - o2.getPopularity();
                if (i == 0) {
                    i = o1.getWeight() - o2.getWeight();
                }
                return (int) i;
            }
        });
        if (cachePointlist != null) {
            CachePoint droppoint = cachePointlist.get(0);
            cachePointlist.remove(droppoint);
            currentSize--;
        }
    }

    //添加新的缓存
    public void addCachePoint(String content) {
        //todo
        if (currentSize <= maxSize) {
            CachePoint cachePoint = new CachePoint(content);
            cachePointlist.add(cachePoint);
        } else {
            CachePoint cachePoint = new CachePoint(content);
            dropCachePoint();
            for (CacheInfo cacheInfo : cacheList) {
                if (cacheInfo.getId().equals(content)) {
                    CachePoint cachePoint1 = new CachePoint(cacheInfo);
                    cachePoint = cachePoint1;
                }
            }
            cachePointlist.add(cachePoint);
        }
        currentSize++;
    }

    //检查缓存中有没有这个内容
    public boolean checkNoContent(String content) {
        if (content != null) {
            if (currentSize == 0) {
                return true;
            }
            if (content != null) {
                for (CachePoint cachePoint : cachePointlist) {
                    if (cachePoint.getContent().equals(content)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public CachePoint getCachePoint(String content) {
        if (content != null) {
            for (CachePoint cachePoint : cachePointlist) {
                if (cachePoint.getContent().equals(content)) {
                    return cachePoint;
                }
            }
        }
        return null;
    }

    //初始化cachemanagement队列
    private void init() {
        int r = (int) (Math.random() * 9);
        for (int i = 0; i < 10; i++) {
            CachePoint cachePoint = new CachePoint(Integer.toString(10 * r + i));
            cachePointlist.add(cachePoint);
        }
        for (int m = 0; m < 100; m++) {
            CacheInfo cacheInfo = new CacheInfo(Integer.toString(m));
            cacheList.add(cacheInfo);
        }
    }

    public void hitratio(String name) {
        if (totalHit != 0) {
            String info="CacheManagement:hitratio hostName: "+name + " hit: " +
                    hit + " " + "totalHit: " + totalHit + " " + "hitratio: "+ (float) hit / (float) totalHit;
            writehitratio(info);
        }
    }
    public void writehitratio(String info){
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File("hitratio.txt");
            fw = new FileWriter(f, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(info);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getTotalHit() {
        return totalHit;
    }

    public void setTotalHit(int totalHit) {
        this.totalHit = totalHit;
    }

}

