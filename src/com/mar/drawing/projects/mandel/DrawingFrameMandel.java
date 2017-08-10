package com.mar.drawing.projects.mandel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mar.drawing.guiobjects.DrawingFrame;

public class DrawingFrameMandel extends DrawingFrame implements ActionListener {

    private static final long serialVersionUID = 6227442272000421489L;

    private final DrawingSurfaceMandel surface;

    private final CommandPanelMandel commandPanel;

    public DrawingFrameMandel(final DrawingSurfaceMandel surface) {
        super(surface);
        this.surface = surface;

        /* Create and set the control panel. */
        commandPanel = new CommandPanelMandel(surface.getMandelbrot());
        getContentPane().add(commandPanel);

        /* Register the confirm button listener. */
        commandPanel.getConfirmButton().addActionListener(this);

        this.setSize(surface.getWidth() + commandPanel.getWidth() + 100, surface.getHeight());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource().equals(commandPanel.getConfirmButton())) {
            commandPanel.setValueInMandelbrot();
            surface.refresh();
        }
    }
}
