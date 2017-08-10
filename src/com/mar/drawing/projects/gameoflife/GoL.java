package com.mar.drawing.projects.gameoflife;

import com.mar.algotools.mathematics.utils.MathOps;

public class GoL {

    private int width;

    private int height;

    private boolean[][] buffer;

    private int currentBuffer = 0;

    private boolean[] patterns;

    public GoL(int pWidth, int pHeight) {
        width = pWidth;
        height = pHeight;

        buffer = new boolean[2][width * height];

        initPatterns();
    }

    public void doStep() {
        int nextBuffer = currentBuffer ^ 1;

        for (int i = 1; i < height - 1; ++i) {
            int surrounding = (buffer[currentBuffer][(i - 1) * width] ? 32 : 0)
                    + (buffer[currentBuffer][(i) * width] ? 16 : 0) + (buffer[currentBuffer][(i + 1) * width] ? 8 : 0)
                    + (buffer[currentBuffer][(i - 1) * width + 1] ? 4 : 0)
                    + (buffer[currentBuffer][(i) * width + 1] ? 2 : 0)
                    + (buffer[currentBuffer][(i + 1) * width + 1] ? 1 : 0);

            for (int j = 1; j < width - 1; ++j) {
                surrounding = (surrounding % 64) * 8 + (buffer[currentBuffer][(i - 1) * width + j + 1] ? 4 : 0)
                        + (buffer[currentBuffer][(i) * width + j + 1] ? 2 : 0)
                        + (buffer[currentBuffer][(i + 1) * width + j + 1] ? 1 : 0);

                buffer[nextBuffer][i * width + j] = patterns[surrounding];
            }
        }

        /* Update border. */
        for (int i = 0; i < height; ++i) {
            buffer[nextBuffer][i * width] = evaluate(0, i);
            buffer[nextBuffer][i * width + width - 1] = evaluate(width - 1, i);
        }

        for (int i = 0; i < width; ++i) {
            buffer[nextBuffer][i] = evaluate(i, 0);
            buffer[nextBuffer][(height - 1) * width + i] = evaluate(i, height - 1);
        }

        currentBuffer = nextBuffer;
    }

    public boolean[] getBuffer() {
        return buffer[currentBuffer];
    }

    public void setCellValue(int pX, int pY, boolean pAlive) {
        buffer[currentBuffer][width * pY + pX] = pAlive;
    }

    private boolean evaluate(int pX, int pY) {
        int surrounding = (buffer[currentBuffer][MathOps.mod((pY - 1), height) * width + MathOps.mod((pX - 1), width)]
                ? 256 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY), height) * width + MathOps.mod((pX - 1), width)] ? 128 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY + 1), height) * width + MathOps.mod((pX - 1), width)] ? 64 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY - 1), height) * width + MathOps.mod((pX), width)] ? 32 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY), height) * width + MathOps.mod((pX), width)] ? 16 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY + 1), height) * width + MathOps.mod((pX), width)] ? 8 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY - 1), height) * width + MathOps.mod((pX + 1), width)] ? 4 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY), height) * width + MathOps.mod((pX + 1), width)] ? 2 : 0)
                + (buffer[currentBuffer][MathOps.mod((pY + 1), height) * width + MathOps.mod((pX + 1), width)] ? 1 : 0);

        return patterns[surrounding];
    }

    /**
     * Precomputes patterns. For each 512 combinations of 3x3 cells, determines
     * if the resulting middle cell is alive at the next iteration. Each pattern
     * is encoded in an integer as follows:<br/>
     * [y-1][x-1] * 256 + [y-1][x] * 32 + [y-1][x+1] * 4 +<br/>
     * [y ][x-1] * 128 + [y ][x] * 16 + [y ][x+1] * 2 +<br/>
     * [y+1][x-1] * 64 + [y+1][x] * 8 + [y+1][x+1] * 1<br/>
     */
    private void initPatterns() {
        patterns = new boolean[512];

        for (int i = 0; i < 512; ++i) {
            boolean cellAlive = ((i >> 4) & 1) == 1;

            int cptOtherCellsAlive = 0;
            cptOtherCellsAlive += i & 1;
            cptOtherCellsAlive += (i >> 1) & 1;
            cptOtherCellsAlive += (i >> 2) & 1;
            cptOtherCellsAlive += (i >> 3) & 1;
            cptOtherCellsAlive += (i >> 5) & 1;
            cptOtherCellsAlive += (i >> 6) & 1;
            cptOtherCellsAlive += (i >> 7) & 1;
            cptOtherCellsAlive += (i >> 8) & 1;

            if (cellAlive && (cptOtherCellsAlive == 2 || cptOtherCellsAlive == 3)) {
                patterns[i] = true;
            } else if (!cellAlive && cptOtherCellsAlive == 3) {
                patterns[i] = true;
            }
        }
    }

}