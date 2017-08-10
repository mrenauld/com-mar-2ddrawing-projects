package com.mar.drawing.projects.gameoflife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mar.drawing.guiobjects.DrawingFrame;

public class DrawingFrameGoL extends DrawingFrame implements ActionListener {

    private static final long serialVersionUID = -7972581445381285887L;

    public DrawingFrameGoL(DrawingSurfaceGoL pSurface) {
        super(pSurface);

        this.setSize(pSurface.getWidth(), pSurface.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent pE) {
        // TODO Auto-generated method stub

    }

}
