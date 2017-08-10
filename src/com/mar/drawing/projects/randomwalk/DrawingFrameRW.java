package com.mar.drawing.projects.randomwalk;

import com.mar.drawing.guiobjects.DrawingFrame;
import com.mar.drawing.guiobjects.DrawingSurface;

public class DrawingFrameRW extends DrawingFrame {

    private static final long serialVersionUID = -6512037739253804167L;

    public DrawingFrameRW(DrawingSurface pSurface) {
        super(pSurface);

        this.setSize(pSurface.getWidth() + 50, pSurface.getHeight() + 50);
    }

}
