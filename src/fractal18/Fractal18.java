/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal18;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author User
 */
public class Fractal18 {

    public static BufferedImage[] bufferImage;

    public static float xBegin = 0;
    public static float xEnd = 0, yBegin = 0, yEnd = 0, xLen, yLen;
    public static Integer tasks;
    public static Integer screenHeight;
    public static Integer screenWidth;
    public static long startTime;
    public static long endTime;

    public static void drawImage(Integer tasks, String initialSize, String initialCoordinates, String outputFileName, boolean quietMode) throws InterruptedException {

        Fractal18.tasks = tasks;
        Fractal18.xBegin = Float.parseFloat(initialCoordinates.split(":")[0]);
        Fractal18.xEnd = Float.parseFloat(initialCoordinates.split(":")[1]);
        Fractal18.yBegin = Float.parseFloat(initialCoordinates.split(":")[2]);
        Fractal18.yEnd = Float.parseFloat(initialCoordinates.split(":")[3]);
        Fractal18.screenWidth = Integer.parseInt(initialSize.split("x")[0]);
        Fractal18.screenHeight = Integer.parseInt(initialSize.split("x")[1]);

        BufferedImage bufferTotal = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = bufferTotal.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, screenWidth + 1, screenHeight + 1);

        Thread[] temp = new Thread[tasks];

        bufferImage = new BufferedImage[tasks];

        for (int x = 0; x < tasks; x++) {
            bufferImage[x] = new BufferedImage(screenWidth + 1, screenHeight / tasks, BufferedImage.TYPE_3BYTE_BGR);
        }

        for (int x = 0; x < temp.length; x++) {
            Computable runnableJob = new Computable();
            temp[x] = new Thread(runnableJob);
            temp[x].setName(Integer.toString(x));
            temp[x].start();
            if (quietMode == false) {
                System.out.println("The thread starts");
            }

        }

        for (Thread temp1 : temp) {
            temp1.join();
        }

        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, screenWidth - 2, screenHeight - 2);

        if (tasks > 1) {
            Graphics g = bufferTotal.getGraphics();
            for (int x = 0; x < temp.length; x++) {

                g.drawImage(bufferImage[x], 0, (x) * (screenHeight / tasks), null);

            }

            g.dispose();
        } else {
            bufferTotal = bufferImage[0];
        }

        try {
            ImageIO.write(bufferTotal, "PNG", new File(outputFileName));
        } catch (IOException ex) {
            Logger.getLogger(Fractal18.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws InterruptedException, ParseException {

        Fractal18.startTime = System.currentTimeMillis();

        Options opt = new Options();

        opt.addOption("t", "tasks", true, "Number of threads");
        opt.addOption("s", "size", true, "Size of the picture to display the fractal");
        opt.addOption("r", "rect", true, "Coordinates to calculate in.");
        opt.addOption("o", "output", true, "Output file name.");
        opt.addOption("q", "quiet", false, "Activates quiet mode.");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(opt, args);

            Boolean quietMode = cl.hasOption("q");
              
            if (!quietMode) {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new Panel());
                frame.pack();
                frame.setVisible(true);
            }

            Integer tasks = Integer.parseInt(cl.getOptionValue("t", "1"));

            String initialSize = cl.getOptionValue("s", "640x480");

            String initialCoordinates = cl.getOptionValue("r", "-2.0:2.0:-2.0:2.0");

            String outputFileName = cl.getOptionValue("o", "zad15.png");

            drawImage(tasks, initialSize, initialCoordinates, outputFileName, quietMode);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Fractal18.endTime = System.currentTimeMillis();
        
        System.out.println("Time spent " + (Fractal18.startTime - Fractal18.endTime)  + " ms.");

    }
}