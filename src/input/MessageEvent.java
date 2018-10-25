/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package input;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

/**
 * A message related external event
 */
public abstract class MessageEvent extends ExternalEvent {
    /**
     * address of the node the message is from
     */
    protected int fromAddr;
    /**
     * address of the node the message is to
     */
    protected int toAddr;
    /**
     * idntifier of the message
     */
    protected String id;

    protected String content;

    /**
     * Creates a message  event
     *
     * @param from Where the message comes from
     * @param to   Who the message goes to
     * @param id   ID of the message
     * @param time Time when the message event occurs
     */
    public MessageEvent(int from, int to, String id, double time) {
        super(time);
        this.fromAddr = from;
        this.toAddr = to;
        this.id = id;
        Random random = new Random();
        int r = (int) ((random.nextGaussian() + 3) * 100 / 6);
        this.content = Integer.toString(r);
        writecontent(this.content);
        //System.out.println(this.content);
    }

    @Override
    public String toString() {
        return "MSG @" + this.time + " " + id;
    }

    public void writecontent(String content) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File("content.txt");
            fw = new FileWriter(f, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(this.content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
