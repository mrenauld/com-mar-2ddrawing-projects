package com.mar.drawing.projects.mandel;

public class Complex {

	public double a;
	public double b;

	public Complex(final double a, final double b) {
		this.a = a;
		this.b = b;
	}

	public void add(final Complex c) {
		a += c.a;
		b += c.b;
	}

	public void cos() {
		final double x = Math.cos(a) * Math.cosh(b);
		final double y = -Math.sin(a) * Math.sinh(b);
		a = x;
		b = y;
	}

	public void divide(final Complex c) {
		final double d = c.a * c.a + c.b * c.b;
		final double x = (a * c.a + b * c.b) / d;
		final double y = (b * c.a - a * c.b) / d;
		a = x;
		b = y;
	}

	public void exp() {
		final double ea = Math.exp(a);
		final double x = ea * Math.cos(b);
		final double y = ea * Math.sin(b);
		a = x;
		b = y;
	}

	public void power(final int p) {
		if (p == 0) {
			a = 1.0;
			b = 0.0;
		}
		final double c = a;
		final double d = b;
		for (int i = 0; i < p - 1; ++i) {
			final double x = a * c - b * d;
			final double y = a * d + b * c;
			a = x;
			b = y;
		}
	}

	public void sin() {
		final double x = Math.sin(a) * Math.cosh(b);
		final double y = Math.cos(a) * Math.sinh(b);
		a = x;
		b = y;
	}

	public void subtract(final Complex c) {
		a -= c.a;
		b -= c.b;
	}

	public void times(final Complex c) {
		final double x = a * c.a - b * c.b;
		final double y = a * c.b + b * c.a;
		a = x;
		b = y;
	}

	public void times(final double d) {
		a *= d;
		b *= d;
	}
}