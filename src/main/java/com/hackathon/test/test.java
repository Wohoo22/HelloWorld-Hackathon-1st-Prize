package com.hackathon.test;

import com.hackathon.entity.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test {
    public static void generateMap(int size) {
        Map map = new Map();
        int i = new Random().nextInt(2);
        int[] startPoint = {0, 0};
        switch (i) {
            case 0:
                startPoint = new int[]{0, (int) Math.floor(size / 2)};
                break;
            case 1:
                startPoint = new int[]{(int) Math.floor(size / 2), 0};
                break;
        }
        List<int[]> rightPoints = new ArrayList<>();
        rightPoints.add(startPoint);
        for (int j = 0; j < (size + 1) * (size + 1); j++) {
            int dir = new Random().nextInt(4);
            int[] nextPoint = {0, 0};
            int[] lastPoint = rightPoints.get(rightPoints.size() - 1);
            switch (dir) {
                case 0: //up
                    nextPoint = new int[]{lastPoint[0], lastPoint[1] + 1};
                    break;
                case 1: //down
                    nextPoint = new int[]{lastPoint[0], lastPoint[1] - 1};
                    break;
                case 2: //left
                    nextPoint = new int[]{lastPoint[0] - 1, lastPoint[1]};
                    break;
                case 3: //right
                    nextPoint = new int[]{lastPoint[0] + 1, lastPoint[1]};
                    break;
            }
            if (nextPoint[0] > 0 && nextPoint[0] <= size && nextPoint[1] > 0 && nextPoint[1] <= size && noInclude(rightPoints, nextPoint)) {
                rightPoints.add(nextPoint);
            }
        }
        map.setStartPoint(startPoint);
        map.setRightPoints(rightPoints);
        map.setFooPoints(null);
        map.setSize(size);
        int monsterQuantity = new Random().nextInt(4);
        List<int[]> monsters = new ArrayList<>();
        for (int j = 0; j <= monsterQuantity; j++) {
            monsters.add(rightPoints.get(new Random().nextInt(rightPoints.size())));
        }
        map.setMonster(monsters);
    }

    public static boolean noInclude(List<int[]> rightPoints, int[] nextPoint) {
        for (int[] point : rightPoints) {
            if (point[0] == nextPoint[0] && point[1] == nextPoint[1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        generateMap(15);
    }
}


