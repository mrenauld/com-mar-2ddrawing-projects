package com.mar.drawing.projects.mandel;

import java.awt.Color;

import com.mar.drawing.guiobjects.color.Palette;

public class Mandelbrot {

	public static final int SET_TYPE_MANDELBROT = 1;
	public static final int SET_TYPE_JULIA = 2;

	public static final int EVAL_ESCAPE_TIME = 0;
	public static final int EVAL_ESCAPE_TIME_SMOOTH = 1;

	private static final double log2 = Math.log(2.0);

	private int setType = SET_TYPE_MANDELBROT;

	private double[][] coordArray;

	private double[][] map;

	private Palette palette;

	private int evalType = EVAL_ESCAPE_TIME;
	private int nbIteration = 1000;
	private double escapeRadius = 4.0;
	private double coef = 1.0;
	private AlgebraicExpression algebraicExpression;
	private double exp = 2.0;
	private double logExp = Math.log(exp);

	public Mandelbrot() {

	}

	public Mandelbrot(final int evalType) {
		this.evalType = evalType;
		init();
	}

	public void compute() {
		map = new double[coordArray[0].length][coordArray[1].length];
		for (int i = 0; i < coordArray[0].length; ++i) {
			for (int j = 0; j < coordArray[1].length; ++j) {
				map[i][j] = eval(coordArray[0][i], coordArray[1][j]) / coef;
			}
		}
	}

	public double getCoef() {
		return coef;
	}

	public Color getColor(final int xIdx, final int yIdx) {
		if (evalType == EVAL_ESCAPE_TIME_SMOOTH) {

			/* Linear interpolation. */
			double nbIt = map[xIdx][yIdx];
			if (nbIt < 0) {
				nbIt = 0.0;
			}
			final int colorIdx1 = (int) Math.floor(nbIt);
			final int colorIdx2 = (int) Math.floor(nbIt) + 1;
			float remainder = (float) (nbIt - colorIdx1);

			if (remainder < 0f) {
				remainder = 0f;
			}
			if (remainder > 1f) {
				remainder = 1f;
			}

			final float[] c1 = palette.getColorRGBMod(colorIdx1);
			final float[] c2 = palette.getColorRGBMod(colorIdx2);
			final float[] cInterp = new float[3];
			cInterp[0] = (c2[0] - c1[0]) * remainder + c1[0];
			cInterp[1] = (c2[1] - c1[1]) * remainder + c1[1];
			cInterp[2] = (c2[2] - c1[2]) * remainder + c1[2];

			return new Color(cInterp[0], cInterp[1], cInterp[2]);
		} else {
			return palette.getColorMod((int) Math.round(map[xIdx][yIdx]));
		}
	}

	public double[][] getCoordArray() {
		return coordArray;
	}

	public double getEscapeRadius() {
		return escapeRadius;
	}

	public double getExp() {
		return exp;
	}

	public int getNbIteration() {
		return nbIteration;
	}

	public void init() {
		if (evalType == EVAL_ESCAPE_TIME) {
			nbIteration = 100;
			escapeRadius = 4;
		} else if (evalType == EVAL_ESCAPE_TIME_SMOOTH) {
			nbIteration = 100;
			escapeRadius = Math.pow(2.0, 8.0);
		}
	}

	public void setAlgebraicExpression(final String s) {
		algebraicExpression = new AlgebraicExpression(s);
	}

	public void setCoef(final double coef) {
		this.coef = coef;
	}

	public void setCoordArray(final double[][] coordArray) {
		this.coordArray = coordArray;
	}

	public void setEscapeRadius(final double escapeRadius) {
		this.escapeRadius = escapeRadius;
	}

	public void setEvalType(final int evalType) {
		this.evalType = evalType;
	}

	public void setExp(final double exp) {
		this.exp = exp;
		logExp = Math.log(exp);
	}

	public void setNbIteration(final int nbIteration) {
		this.nbIteration = nbIteration;
	}

	public void setPalette(final Palette palette) {
		this.palette = palette;
	}

	public void setSetType(final int setType) {
		this.setType = setType;
	}

	public void setSmooth(final boolean smooth) {
		if (smooth) {
			evalType = EVAL_ESCAPE_TIME_SMOOTH;
		} else {
			evalType = EVAL_ESCAPE_TIME;
		}
	}

	private double eval(final double cx, final double cy) {
		if (setType == SET_TYPE_MANDELBROT) {
			if (evalType == EVAL_ESCAPE_TIME_SMOOTH) {
				return evalEscapeTimeSmoothMandel(cx, cy);
			} else {
				return evalEscapeTimeMandel(cx, cy);
			}
		} else {
			if (evalType == EVAL_ESCAPE_TIME_SMOOTH) {
				return evalJuliaSmooth(cx, cy);
			} else {
				return evalJulia(cx, cy);
			}
		}
	}

	private double evalEscapeTimeMandel(final double cx, final double cy) {
		double x = 0.0;
		double y = 0.0;

		double cpt = 0;
		while (cpt < nbIteration && x * x + y * y < escapeRadius) {
			final double xtemp = x * x - y * y + cx;
			y = 2 * x * y + cy;
			x = xtemp;
			cpt++;
		}

		if (cpt == nbIteration)
			cpt = 0;

		return cpt;
	}

	private double evalEscapeTimeSmoothMandel(final double cx, final double cy) {
		double x = 0.0;
		double y = 0.0;

		double cpt = 0;
		while (cpt < nbIteration && x * x + y * y < escapeRadius) {
			final double xtemp = x * x - y * y + cx;
			y = 2 * x * y + cy;
			x = xtemp;
			cpt++;
		}

		if (cpt < nbIteration) {
			final double zn = Math.sqrt(x * x + y * y);
			final double nu = Math.log(Math.log(zn) / log2) / log2;
			cpt = cpt + 1 - nu;
		}

		if (cpt == nbIteration)
			cpt = 0;

		return cpt;
	}

	private double evalJulia(final double cx, final double cy) {
		double x = cx;
		double y = cy;

		double cpt = 0;
		while (cpt < nbIteration && x * x + y * y < escapeRadius) {
			final Complex c = algebraicExpression.eval(new Complex(x, y));
			x = c.a;
			y = c.b;
			cpt++;
		}

		if (cpt == nbIteration)
			cpt = 0;

		return cpt;
	}

	private double evalJuliaSmooth(final double cx, final double cy) {
		double x = cx;
		double y = cy;

		double cpt = 0;
		while (cpt < nbIteration && x * x + y * y < escapeRadius) {
			final Complex c = algebraicExpression.eval(new Complex(x, y));
			x = c.a;
			y = c.b;
			cpt++;
		}

		if (cpt < nbIteration) {
			final double zn = Math.sqrt(x * x + y * y);
			final double nu = Math.log(Math.log(zn) / logExp) / logExp;
			cpt = cpt + 1 - nu;
		}

		if (cpt == nbIteration)
			cpt = 0;

		return cpt;
	}
}