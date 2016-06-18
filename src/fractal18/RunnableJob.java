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
public class RunnableJob implements Runnable {
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

    public RunnableJob() {

    }


    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        float threadStart = RunMe.yBegin;

        RunMe.xLen= (int) (Math.abs(RunMe.xBegin)+Math.abs(RunMe.xEnd));
        RunMe.yLen= (int) (Math.abs(RunMe.yBegin)+Math.abs(RunMe.yEnd));

        if(Integer.parseInt(thread.getName())!=0){
            threadStart = threadStart +((RunMe.yLen/RunMe.tasks)*Integer.parseInt(thread.getName()));
        }

        double yStep = 1.0/(RunMe.screenHeight*1.2);

        for (int i = Integer.parseInt(thread.getName()) * (RunMe.screenHeight / RunMe.tasks); i < (Integer.parseInt(thread.getName()) + 1) * (RunMe.screenHeight / RunMe.tasks); i++) {


            double yPoint = threadStart + RunMe.yLen * yStep;

            int yPx = (int) (Math.abs((yPoint- threadStart)) * ( RunMe.screenHeight / RunMe.yLen));
            double xStep = 1.0/(RunMe.screenWidth*1.2);

            for (int j = 0; j < (RunMe.screenWidth); j++) {

                double xPoint = RunMe.xBegin + RunMe.xLen * xStep;
                int xPx = (int) (Math.abs((xPoint-RunMe.xBegin)) * (RunMe.screenWidth/ RunMe.xLen));

                int r = z_check(new Complex(xPoint, yPoint));

                if (r == 0) { // inside ...
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x00ff00);
                } else if (r <= 10) { // outside ... (rapid move)
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xFFFFFF);
                    // close to inside cases ...
                } else if (10 < r && r <= 50) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0033EE);
                } else if (r == 11) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000ff);
                } else if (r == 12) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000ee);
                } else if (r == 13) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000dd);
                } else if (r == 14) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000cc);
                } else if (r == 15) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000bb);
                } else if (r == 16) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x0000aa);
                } else if (r == 17) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000099);
                } else if (r == 18) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000088);
                } else if (r == 19) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000077);
                } else if (r == 20) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x000066);
                } else if (20 < r && r <= 30) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x666600);
                } else if (30 < r && r <= 40) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x777700);
                } else if (40 < r && r <= 50) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x888800);
                } else if (50 < r && r <= 100) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0x999900);
                } else if (100 < r && r <= 150) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xaaaa00);
                } else if (150 < r && r <= 150) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xbbbb00);
                } else if (200 < r && r <= 150) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xcccc00);
                } else if (350 < r && r <= 300) {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xdddd00);
                } else {
                    RunMe.bufferImage[Integer.parseInt(thread.getName())].setRGB(xPx, yPx, 0xeeee00);
                }
                xStep += 1.0/(RunMe.screenWidth);

            }
            yStep += 1.0/(RunMe.screenHeight);

        }
    }
    
    

    }

