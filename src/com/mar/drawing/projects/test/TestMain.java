package com.mar.drawing.projects.test;

import com.mar.drawing.projects.mandel.DrawingFrameMandel;
import com.mar.drawing.projects.mandel.DrawingSurfaceMandel;

public class TestMain {

    public static void main(final String[] args) {

        // DrawingSurfaceRW surface = new DrawingSurfaceRW();
        // DrawingFrameRW frame = new DrawingFrameRW(surface);

        DrawingSurfaceMandel surface = new DrawingSurfaceMandel();
        DrawingFrameMandel frame = new DrawingFrameMandel(surface);

        // DrawingSurfaceGoL surface = new DrawingSurfaceGoL();
        // DrawingFrameGoL frame = new DrawingFrameGoL(surface);

        // while (true) {
        // try {
        // Thread.sleep(10);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // surface.repaint();
        // }
    }

}
