package com.mar.drawing.projects.randomwalk;

import java.util.HashSet;

public class RandomWalk {

    private int posX;

    private int posY;

    private int minX;

    private int maxX;

    private int minY;

    private int maxY;

    private HashSet<Integer> edgeSet;

    public RandomWalk() {
        reset();
    }

    public int edgeToInt(int pPosX, int pPosY, int pDirection) {
        return (pPosX << 16) + (pPosY << 1) + pDirection;
    }

    public HashSet<Integer> getEdgeSet() {
        return edgeSet;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int[] getNextPos(int pPosX, int pPosY, int pDirection) {
        int[] posOut = new int[2];
        if (pDirection == 0) {
            posOut[0] = pPosX + 1;
            posOut[1] = pPosY;
        } else if (pDirection == 1) {
            posOut[0] = pPosX;
            posOut[1] = pPosY - 1;
        } else if (pDirection == 2) {
            posOut[0] = pPosX - 1;
            posOut[1] = pPosY;
        } else {
            posOut[0] = pPosX;
            posOut[1] = pPosY + 1;
        }
        return posOut;
    }

    public void walk() {
        int direction = (int) (Math.random() * 4);

        if (direction == 2) {
            addEdge(posX - 1, posY, 0);
        } else if (direction == 3) {
            addEdge(posX, posY + 1, 1);
        } else {
            addEdge(posX, posY, direction);
        }

        int[] newPos = getNextPos(posX, posY, direction);
        posX = newPos[0];
        posY = newPos[1];

        if (posX < minX) {
            minX = posX;
        } else if (posX > maxX) {
            maxX = posX;
        }

        if (posY < minY) {
            minY = posY;
        } else if (posY > maxY) {
            maxY = posY;
        }
    }

    private void addEdge(int pPosX, int pPosY, int pDirection) {
        int edgeInt = edgeToInt(pPosX, pPosY, pDirection);
        if (!edgeSet.contains(edgeInt)) {
            edgeSet.add(edgeInt);
        }
    }

    private void reset() {
        posX = 0;
        posY = 0;
        minX = -1;
        maxX = 1;
        minY = -1;
        maxY = 1;
        edgeSet = new HashSet<Integer>();
    }
}