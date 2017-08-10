package com.mar.drawing.projects.randomwalk;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;

import com.mar.drawing.guiobjects.Coord;
import com.mar.drawing.guiobjects.DrawingSurface;
import com.mar.drawing.guiobjects.Pixel;

public class DrawingSurfaceRW extends DrawingSurface {

    private static final long serialVersionUID = -1552545951026895993L;

    private RandomWalk randomWalk;

    public DrawingSurfaceRW() {
        super();

        coordLengthX = 5.0;
        coordLengthY = 5.0;

        /* Dimension. */
        final Dimension dimension = new Dimension(200, 200);
        this.setSize(dimension);
        setPreferredSize(dimension);
    }

    @Override
    protected void doDrawing(final Graphics g) {
        // this.showGrid(g);

        final Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.BLACK);

        randomWalk.walk();
        HashSet<Integer> edgeSet = randomWalk.getEdgeSet();

        int minX = randomWalk.getMinX() - 5;
        int maxX = randomWalk.getMaxX() + 5;
        double centerX = (minX + maxX) / 2.0;

        int minY = randomWalk.getMinY() - 5;
        int maxY = randomWalk.getMaxY() + 5;
        double centerY = (minY + maxY) / 2.0;

        coordCenter = new Coord(centerX, centerY);
        coordLengthX = (maxX - centerX) * 2.0;
        coordLengthY = (maxY - centerY) * 2.0;

        Coord coord = new Coord(0, 0);
        Pixel pixel = this.coordToPixel(coord);
        this.showPixel(g, pixel);

        coord = new Coord(1, 0);
        pixel = this.coordToPixel(coord);
        this.showPixel(g, pixel);

        coord = new Coord(0, 1);
        pixel = this.coordToPixel(coord);
        this.showPixel(g, pixel);

        g2d.setStroke(new BasicStroke(1));

        for (int i = randomWalk.getMinX(); i <= randomWalk.getMaxX(); ++i) {
            for (int j = randomWalk.getMinY(); j <= randomWalk.getMaxY(); ++j) {
                int edgeInt0 = randomWalk.edgeToInt(i, j, 0);
                int edgeInt1 = randomWalk.edgeToInt(i, j, 1);

                if (edgeSet.contains(edgeInt0)) {
                    Coord coord0 = new Coord(i, j);
                    Coord coord1 = new Coord(i + 1, j);
                    Pixel pixel0 = this.coordToPixel(coord0);
                    Pixel pixel1 = this.coordToPixel(coord1);
                    g2d.drawLine(pixel0.w, pixel0.h, pixel1.w, pixel1.h);
                }

                if (edgeSet.contains(edgeInt1)) {
                    Coord coord0 = new Coord(i, j);
                    Coord coord1 = new Coord(i, j - 1);
                    Pixel pixel0 = this.coordToPixel(coord0);
                    Pixel pixel1 = this.coordToPixel(coord1);
                    g2d.drawLine(pixel0.w, pixel0.h, pixel1.w, pixel1.h);
                }
            }
        }
    }

    @Override
    protected void initCompute() {
        randomWalk = new RandomWalk();
    }
}
