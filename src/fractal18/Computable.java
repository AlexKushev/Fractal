/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal18;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author User
 */
public class Computable implements Runnable {

    public static Complex z_iter(Complex z, Complex c) {
        return z.cos().multiply(c);
    }

    public static int z_check(Complex c) {
        Complex z0 = new Complex(0.0, 0.0);

        Complex z_prev = z0;
        Complex z_i = null;

        int steps = 0;

        Double d = null;

        for (int i = 0; i < 1000; i++) {

            z_i = z_iter(z_prev, c);
            z_prev = z_i;

            d = new Double(z_prev.getReal());

            if (d.isInfinite() || d.isNaN()) {
                steps = i;
                break;
            }
        }
        return steps;
    }

    public Computable() {

    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        long currentThreadStartTime = System.currentTimeMillis();
        if (!Fractal18.quietMode) {
            System.out.println("Thread[" + (thread.getName() + 1) + "] " + "started");
        }
        float threadStart = Fractal18.yBegin;

        Fractal18.xLen = (int) (Math.abs(Fractal18.xBegin) + Math.abs(Fractal18.xEnd));
        Fractal18.yLen = (int) (Math.abs(Fractal18.yBegin) + Math.abs(Fractal18.yEnd));

        if (Integer.parseInt(thread.getName()) != 0) {
            threadStart = threadStart + ((Fractal18.yLen / Fractal18.tasks) * Integer.parseInt(thread.getName()));
        }

        double yStep = 1.0 / (Fractal18.screenHeight * 1.2);

        for (int i = Integer.parseInt(thread.getName()) * (Fractal18.screenHeight / Fractal18.tasks); i < (Integer.parseInt(thread.getName()) + 1) * (Fractal18.screenHeight / Fractal18.tasks); i++) {

            double yPoint = threadStart + Fractal18.yLen * yStep;

            int yPx = (int) (Math.abs((yPoint - threadStart)) * (Fractal18.screenHeight / Fractal18.yLen));
            double xStep = 1.0 / (Fractal18.screenWidth * 1.2);

            for (int j = 0; j < (Fractal18.screenWidth); j++) {

                double xPoint = Fractal18.xBegin + Fractal18.xLen * xStep;
                int xPx = (int) (Math.abs((xPoint - Fractal18.xBegin)) * (Fractal18.screenWidth / Fractal18.xLen));

                int r = z_check(new Complex(xPoint, yPoint));

                if (r == 0) { // inside ...
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000000);
                } else if (r <= 10) { // outside ... (rapid move)
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xFFFFFF);
                    // close to inside cases ...
                } else if (10 < r && r <= 50) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0033EE);
                } else if (r == 11) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000ff);
                } else if (r == 12) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000ee);
                } else if (r == 13) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000dd);
                } else if (r == 14) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000cc);
                } else if (r == 15) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000bb);
                } else if (r == 16) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000aa);
                } else if (r == 17) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000099);
                } else if (r == 18) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000088);
                } else if (r == 19) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000077);
                } else if (r == 20) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000066);
                } else if (20 < r && r <= 30) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x666600);
                } else if (30 < r && r <= 40) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x777700);
                } else if (40 < r && r <= 50) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x888800);
                } else if (50 < r && r <= 100) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x999900);
                } else if (100 < r && r <= 150) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xaaaa00);
                } else if (150 < r && r <= 150) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xbbbb00);
                } else if (200 < r && r <= 150) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xcccc00);
                } else if (350 < r && r <= 300) {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xdddd00);
                } else {
                    Fractal18.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xeeee00);
                }
                xStep += 1.0 / (Fractal18.screenWidth);

            }
            yStep += 1.0 / (Fractal18.screenHeight);

        }

        if (!Fractal18.quietMode) {
            long currentThreadEndTime = System.currentTimeMillis();
            System.out.println("Thread[" + (thread.getName() + 1) + "] " + "stoped");
            System.out.println("Thread[" + (thread.getName() + 1) + "] execution time was " + (currentThreadStartTime - currentThreadEndTime));
        }

    }

}
