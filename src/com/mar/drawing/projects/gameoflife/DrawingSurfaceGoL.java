package com.mar.drawing.projects.gameoflife;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.mar.drawing.guiobjects.DrawingSurface;

public class DrawingSurfaceGoL extends DrawingSurface {

    private static final long serialVersionUID = -6299038469447562489L;

    private static int golWidth = 256;

    private static int golHeight = 256;

    private GoL gol;

    public DrawingSurfaceGoL() {
        super();

        /* Dimension. */
        final Dimension dimension = new Dimension(golWidth, golHeight);
        this.setSize(dimension);
        setPreferredSize(dimension);
    }

    @Override
    protected void doDrawing(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(1));

        gol.doStep();

        boolean[] buffer = gol.getBuffer();

        for (int i = 0; i < golHeight; ++i) {
            for (int j = 0; j < golWidth; ++j) {
                if (buffer[i * golHeight + j]) {
                    g2d.drawLine(j, i, j, i);
                }
            }
        }
    }

    @Override
    protected void initCompute() {
        gol = new GoL(golWidth, golHeight);

        for (int i = 0; i < golWidth; ++i) {
            for (int j = 0; j < golHeight; ++j) {
                double rand = Math.random();
                if (rand > 0.75) {
                    gol.setCellValue(i, j, true);
                }
            }
        }

        // gol.setCellValue(70, 40, true);
        // gol.setCellValue(71, 40, true);
        // gol.setCellValue(72, 40, true);
        // gol.setCellValue(72, 39, true);
        // gol.setCellValue(71, 38, true);
    }
}
