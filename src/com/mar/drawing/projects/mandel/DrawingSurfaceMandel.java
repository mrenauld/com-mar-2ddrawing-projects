package com.mar.drawing.projects.mandel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.mar.drawing.guiobjects.Coord;
import com.mar.drawing.guiobjects.DrawingSurface;
import com.mar.drawing.guiobjects.DrawingUtils;
import com.mar.drawing.guiobjects.Pixel;
import com.mar.drawing.guiobjects.color.Palette;
import com.mar.drawing.guiobjects.color.PaletteFactory;

public class DrawingSurfaceMandel extends DrawingSurface {

    private static final long serialVersionUID = 3160740135483692922L;

    /** Mandelbrot object. */
    private Mandelbrot mandelbrot;
    /** Palette for the coloration of the Mandelbrot set. */
    private Palette palette;
    /** Coordinates of the pixels in the displayed plane. */
    private double[][] coordArray;

    /** Switch indicating if the selection rectangle is drawn or not. */
    private boolean drawSelectionRectangle = false;

    /**
     * Returns the Mandelbrot object.
     *
     * @return
     */
    public Mandelbrot getMandelbrot() {
        return mandelbrot;
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyChar() == '+') {
            zoomIn();
        } else if (e.getKeyChar() == '=') {
            zoomDefault();
        } else if (e.getKeyChar() == '-') {
            zoomOut();
        } else if (e.getKeyChar() == '*') {
            zoomSquare();
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        super.mouseReleased(e);

        /* Set the selection rectangle. */
        drawSelectionRectangle = true;
        this.repaint();
    }

    /**
     * Recomputes the Mandelbrot set and repaint the surface.
     */
    public void refresh() {
        computeMandelbrot();

        /* No selection rectangle. */
        drawSelectionRectangle = false;

        this.repaint();
    }

    @Override
    protected void doDrawing(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(1));

        final Coord coord = new Coord();
        Pixel pixel = new Pixel();
        for (int i = 0; i < coordArray[0].length; ++i) {
            coord.x = coordArray[0][i];
            for (int j = 0; j < coordArray[1].length; ++j) {
                coord.y = coordArray[1][j];
                pixel = coordToPixel(coord);
                g2d.setColor(mandelbrot.getColor(i, j));
                g2d.drawLine(pixel.w, pixel.h, pixel.w, pixel.h);
            }
        }

        if (drawSelectionRectangle) {
            g2d.setColor(Color.RED);
            final int w = Math.min(mousePressed.w, mouseReleased.w);
            final int h = Math.min(mousePressed.h, mouseReleased.h);
            final int width = Math.max(mousePressed.w, mouseReleased.w) - w;
            final int height = Math.max(mousePressed.h, mouseReleased.h) - h;
            g2d.drawRect(w, h, width, height);
        }
    }

    @Override
    protected void initCompute() {
        /* Create a new Mandelbrot object. */
        if (mandelbrot == null) {
            mandelbrot = new Mandelbrot(Mandelbrot.EVAL_ESCAPE_TIME_SMOOTH);
        } else {
            // mandelbrot.init();
        }

        /* Create and set the Palette. */
        palette = PaletteFactory.getColorPalette1();
        mandelbrot.setPalette(palette);

        /* Set the displayed plane. */
        coordCenter = new Coord(-0.75, 0.0);
        coordLengthX = 4;
        coordLengthY = 4;

        /* Compute the Mandelbrot set. */
        computeMandelbrot();

        /* No selection rectangle. */
        drawSelectionRectangle = false;
    }

    /**
     * Computes the Mandelbrot set for the new displayed plane.
     */
    private void computeMandelbrot() {
        /* Compute the plane coordinates corresponding to the pixels. */
        coordArray = DrawingUtils.getCoordArrayForPixelsOfSurface(this);
        mandelbrot.setCoordArray(coordArray);

        /* Compute the color level for the points. */
        System.out.print("Mandelbrot computation starts...");
        mandelbrot.compute();
        System.out.println(" Done.");
    }

    /**
     * Set the view to the default zoom level and centers it. Repaint the panel.
     */
    private void zoomDefault() {
        initCompute();
        this.repaint();
    }

    /**
     * Set the view to the current selection rectangle. Repaint the panel.
     */
    private void zoomIn() {
        /* Check if the selection rectangle is correctly set. */
        final int width = Math.abs(mouseReleased.w - mousePressed.w);
        final int height = Math.abs(mouseReleased.h - mousePressed.h);

        /* If the selection triangle is too small, do nothing. */
        if (width < 10 || height < 10) {
            System.out.println("Rectangle is too small in one dimension. No zoom in.");
            return;
        }

        final Coord c1 = pixelToCoord(mousePressed);
        final Coord c2 = pixelToCoord(mouseReleased);

        coordCenter.x = (c1.x + c2.x) / 2.0;
        coordCenter.y = (c1.y + c2.y) / 2.0;
        coordLengthX = Math.abs(c2.x - c1.x);
        coordLengthY = Math.abs(c2.y - c1.y);

        computeMandelbrot();

        drawSelectionRectangle = false;

        this.repaint();
    }

    private void zoomOut() {
        final double max = Math.max(2.0 * coordLengthX, 2.0 * coordLengthY);

        coordLengthX = max;
        coordLengthY = max;

        computeMandelbrot();

        drawSelectionRectangle = false;

        this.repaint();
    }

    private void zoomSquare() {
        final double max = Math.max(coordLengthX, coordLengthY);

        coordLengthX = max;
        coordLengthY = max;

        computeMandelbrot();

        drawSelectionRectangle = false;

        this.repaint();
    }

}
