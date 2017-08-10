package com.mar.drawing.projects.mandel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CommandPanelMandel extends JPanel {

    private static final long serialVersionUID = -1335755124661391424L;

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 600;

    private final Mandelbrot mandelbrot;

    private JRadioButton radioButtonMandel;
    private JRadioButton radioButtonJulia;
    private JLabel labelExpression;
    private JTextField fieldExpression;
    private JRadioButton radioButtonNoSmooth;
    private JRadioButton radioButtonSmooth;
    private JLabel labelExp;
    private JTextField fieldExp;
    private JLabel labelNbIteration;
    private JTextField fieldNbIteration;
    private JLabel labelEscapeRadius;
    private JTextField fieldEscapeRadius;
    private JLabel labelCoef;
    private JTextField fieldCoef;
    private JButton buttonConfirm;

    public CommandPanelMandel(final Mandelbrot mandelbrot) {
        this.mandelbrot = mandelbrot;

        init();

        if (mandelbrot != null) {
            updateValues();
        }
    }

    public JButton getConfirmButton() {
        return buttonConfirm;
    }

    public void setValueInMandelbrot() {

        if (radioButtonMandel.isSelected()) {
            mandelbrot.setSetType(Mandelbrot.SET_TYPE_MANDELBROT);
        } else {
            mandelbrot.setSetType(Mandelbrot.SET_TYPE_JULIA);
        }
        mandelbrot.setNbIteration(parseIntValue(fieldNbIteration.getText()));
        mandelbrot.setEscapeRadius(parseDoubleValue(fieldEscapeRadius.getText()));
        mandelbrot.setCoef(parseDoubleValue(fieldCoef.getText()));
        mandelbrot.setAlgebraicExpression(fieldExpression.getText());
        if (radioButtonSmooth.isSelected()) {
            mandelbrot.setSmooth(true);
        } else {
            mandelbrot.setSmooth(false);
        }
        mandelbrot.setExp(parseDoubleValue(fieldExp.getText()));

        updateValues();
    }

    private void init() {
        final Dimension dimension = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setSize(dimension);
        setPreferredSize(dimension);

        setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;

        radioButtonMandel = new JRadioButton("Mandelbrot set");
        radioButtonMandel.setSelected(true);
        radioButtonJulia = new JRadioButton("Julia set");

        final ButtonGroup groupSet = new ButtonGroup();
        groupSet.add(radioButtonMandel);
        groupSet.add(radioButtonJulia);

        c.gridx = 1;
        this.add(radioButtonMandel, c);

        c.gridy++;

        c.gridx = 1;
        this.add(radioButtonJulia, c);

        c.gridy++;

        labelExpression = new JLabel("Expression ");
        c.gridx = 0;
        this.add(labelExpression, c);
        fieldExpression = new JTextField(30);
        c.gridx = 1;
        this.add(fieldExpression, c);

        c.gridy++;

        radioButtonNoSmooth = new JRadioButton("Not smooth");
        radioButtonSmooth = new JRadioButton("Smooth");
        radioButtonSmooth.setSelected(true);

        final ButtonGroup groupSmooth = new ButtonGroup();
        groupSmooth.add(radioButtonNoSmooth);
        groupSmooth.add(radioButtonSmooth);

        c.gridx = 1;
        this.add(radioButtonNoSmooth, c);

        c.gridy++;

        c.gridx = 1;
        this.add(radioButtonSmooth, c);

        c.gridy++;

        labelExp = new JLabel("Exp or smooth ");
        c.gridx = 0;
        this.add(labelExp, c);
        fieldExp = new JTextField(10);
        c.gridx = 1;
        this.add(fieldExp, c);

        c.gridy++;

        labelNbIteration = new JLabel("Number of iterations ");
        c.gridx = 0;
        this.add(labelNbIteration, c);
        fieldNbIteration = new JTextField(10);
        c.gridx = 1;
        this.add(fieldNbIteration, c);

        c.gridy++;

        labelEscapeRadius = new JLabel("Escape radius ");
        c.gridx = 0;
        this.add(labelEscapeRadius, c);
        fieldEscapeRadius = new JTextField(10);
        c.gridx = 1;
        this.add(fieldEscapeRadius, c);

        c.gridy++;

        labelCoef = new JLabel("Coefficient ");
        c.gridx = 0;
        this.add(labelCoef, c);
        fieldCoef = new JTextField(10);
        c.gridx = 1;
        this.add(fieldCoef, c);

        c.gridy++;

        buttonConfirm = new JButton("Confirm");
        c.gridx = 0;
        this.add(buttonConfirm, c);
    }

    private double parseDoubleValue(final String valueString) {
        double v = 1.0;
        if (valueString != null && !valueString.trim().equals("")) {
            try {
                v = Double.parseDouble(valueString);
            } catch (final NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return v;
    }

    private int parseIntValue(final String valueString) {
        int v = 1;
        if (valueString != null && !valueString.trim().equals("")) {
            try {
                v = Integer.parseInt(valueString);
            } catch (final NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return v;
    }

    private void updateValues() {
        fieldNbIteration.setText(Integer.toString(mandelbrot.getNbIteration()));
        fieldEscapeRadius.setText(Double.toString(mandelbrot.getEscapeRadius()));
        fieldCoef.setText(Double.toString(mandelbrot.getCoef()));
        fieldExp.setText(Double.toString(mandelbrot.getExp()));
    }
}
