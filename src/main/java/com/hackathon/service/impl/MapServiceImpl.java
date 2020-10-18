package com.hackathon.service.impl;

import com.hackathon.entity.Map;
import com.hackathon.entity.Response;
import com.hackathon.enums.DieReason;
import com.hackathon.enums.Status;
import com.hackathon.repository.UserRepository;
import com.hackathon.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MapServiceImpl implements MapService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map generateMap(int size) {
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
        int lastDir = 0;
        int dir = 0;
        for (int j = 0; j < (size + 1) * (size + 1); j++) {
            if (j % 2 != 0) {
                dir = lastDir;
            } else {
                dir = new Random().nextInt(4);
                lastDir = dir;
            }
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

            if (nextPoint[0] >= 0 && nextPoint[0] <= size && nextPoint[1] >= 0 && nextPoint[1] <= size && noInclude(rightPoints, nextPoint)) {
                rightPoints.add(nextPoint);
            }
        }
        map.setStartPoint(startPoint);
        map.setRightPoints(rightPoints);
        map.setSize(size);
        int monsterQuantity = new Random().nextInt(size);
        List<int[]> monsters = new ArrayList<>();
        for (int j = 0; j <= monsterQuantity; j++) {
            int[] monster = rightPoints.get(new Random().nextInt(rightPoints.size()));
            if (!Arrays.equals(monster, startPoint) && !aroundInclude(monsters, monster) && !oneEachSideInclude(rightPoints, monster)) {
                monsters.add(monster);
            }
        }
        map.setMonster(monsters);

        List<int[]> fooPoints = new ArrayList<>();
        int[] pt1 = new int[]{0, rightPoints.get((int) Math.floor(rightPoints.size() / 4))[1]};
        int[] pt2 = new int[]{0, rightPoints.get((int) Math.floor(rightPoints.size() / 2))[1]};
        fooPoints.add(pt1);
        for (int k = 0; k <= size; k++) {
            int[] nxtPt = new int[]{fooPoints.get(fooPoints.size() - 1)[0] + 1, fooPoints.get(fooPoints.size() - 1)[1]};
            if (!noInclude(rightPoints, nxtPt)) {
                break;
            }
            fooPoints.add(nxtPt);
        }
        fooPoints.add(pt2);
        for (int k = 0; k <= size; k++) {
            int[] nxtPt = new int[]{fooPoints.get(fooPoints.size() - 1)[0] + 1, fooPoints.get(fooPoints.size() - 1)[1]};
            if (!noInclude(rightPoints, nxtPt)) {
                break;
            }
            fooPoints.add(nxtPt);
        }
        int[] pt3 = new int[]{rightPoints.get((int) Math.floor(rightPoints.size() * 3 / 4))[0], 0};
        int[] pt4 = new int[]{rightPoints.get(rightPoints.size() - 1)[0], 0};
        fooPoints.add(pt3);
        for (int k = 0; k <= size; k++) {
            int[] nxtPt = new int[]{fooPoints.get(fooPoints.size() - 1)[0], fooPoints.get(fooPoints.size() - 1)[1] + 1};
            if (!noInclude(rightPoints, nxtPt)) {
                break;
            }
            fooPoints.add(nxtPt);
        }
        fooPoints.add(pt4);
        for (int k = 0; k <= size; k++) {
            int[] nxtPt = new int[]{fooPoints.get(fooPoints.size() - 1)[0], fooPoints.get(fooPoints.size() - 1)[1] + 1};
            if (!noInclude(rightPoints, nxtPt)) {
                break;
            }
            fooPoints.add(nxtPt);
        }
        map.setFooPoints(fooPoints);

        List<int[]> momPoints = new ArrayList<>();
        if (!Arrays.equals(pt1, startPoint) && noInclude(monsters, pt1)) {
            momPoints.add(pt1);
        }
        if (!Arrays.equals(pt2, startPoint) && noInclude(monsters, pt2)) {
            momPoints.add(pt2);
        }
        if (!Arrays.equals(pt3, startPoint) && noInclude(monsters, pt3)) {
            momPoints.add(pt3);
        }
        if (!Arrays.equals(pt4, startPoint) && noInclude(monsters, pt4)) {
            momPoints.add(pt4);
        }
        map.setMomPoints(momPoints);

        rightPoints.add(rightPoints.get((int) Math.floor(rightPoints.size() * 3 / 4)));

        return map;
    }

    public static boolean noInclude(List<int[]> pointList, int[] thePoint) {
        for (int[] point : pointList) {
            if (point[0] == thePoint[0] && point[1] == thePoint[1]) {
                return false;
            }
        }
        return true;
    }

    public boolean twoSideInclude(List<int[]> pointList, int[] thePoint) {
        int[] left = new int[]{thePoint[0] - 1, thePoint[1]};
        int[] right = new int[]{thePoint[0] + 1, thePoint[1]};
        if (!noInclude(pointList, left)) {
            return true;
        }
        if (!noInclude(pointList, right)) {
            return true;
        }
        return false;
    }

    public boolean aroundInclude(List<int[]> pointList, int[] thePoint) {
        int[] left = new int[]{thePoint[0] - 1, thePoint[1]};
        int[] right = new int[]{thePoint[0] + 1, thePoint[1]};
        int[] up = new int[]{thePoint[0], thePoint[1] + 1};
        int[] down = new int[]{thePoint[0], thePoint[1] - 1};
        if (!noInclude(pointList, left)) {
            return true;
        }
        if (!noInclude(pointList, right)) {
            return true;
        }
        if (!noInclude(pointList, down)) {
            return true;
        }
        if (!noInclude(pointList, up)) {
            return true;
        }
        return false;
    }

    public boolean oneEachSideInclude(List<int[]> pointList, int[] thePoint) {
        int[] left = new int[]{thePoint[0] - 1, thePoint[1]};
        int[] right = new int[]{thePoint[0] + 1, thePoint[1]};
        int[] up = new int[]{thePoint[0], thePoint[1] + 1};
        int[] down = new int[]{thePoint[0], thePoint[1] - 1};
        if (!noInclude(pointList, left) && !noInclude(pointList, up) && noInclude(pointList, right) && noInclude(pointList, down)) {
            return true;
        }
        if (!noInclude(pointList, left) && !noInclude(pointList, down) && noInclude(pointList, right) && noInclude(pointList, up)) {
            return true;
        }
        if (!noInclude(pointList, right) && !noInclude(pointList, up) && noInclude(pointList, left) && noInclude(pointList, down)) {
            return true;
        }
        if (!noInclude(pointList, right) && !noInclude(pointList, down) && noInclude(pointList, left) && noInclude(pointList, up)) {
            return true;
        }
        return false;
    }

    public Response checkUserPath(Map map, List<int[]> userPoints, String username) {
        Response response = new Response();
        response.setMap(map);
        response.setUserPoints(userPoints);
        response.setRightWay(true);
        response.setDieAtStep(userPoints.size() + 1);
        int step = 0;
        int momSaved = 0;
        for (int[] point : userPoints) {
            step++;
            if (noInclude(map.getRightPoints(), point) && noInclude(map.getFooPoints(), point)) {
                response.setDieReason(DieReason.FALL);
                response.setDieAtStep(step);
                response.setRightWay(false);
                response.setStatus(Status.UNDONE);
                break;
            }
            if (!noInclude(map.getMonster(), point)) {
                response.setDieReason(DieReason.MONSTER);
                response.setDieAtStep(step);
                response.setRightWay(false);
                response.setStatus(Status.UNDONE);
                break;
            }
            if (!noInclude(map.getMomPoints(), point)) {
                momSaved++;
            }
        }
        int[] lastPoint = userPoints.get(userPoints.size() - 1);
        if (response.isRightWay()) {
            if (Arrays.equals(lastPoint, map.getRightPoints().get(map.getRightPoints().size() - 1))) {
                if (momSaved == map.getMomPoints().size()) {
                    response.setStatus(Status.DONE);
                    response.setUnlockLevel(userRepository.increaseLevel(username));
                } else {
                    response.setDieReason(DieReason.MOM_DIED);
                    response.setDieAtStep(map.getRightPoints().size());
                    response.setRightWay(false);
                    response.setStatus(Status.UNDONE);
                }
            } else {
                response.setStatus(Status.UNDONE);
                response.setRightWay(false);
            }
        }
        return response;
    }

    public Map generateBigMap(int size, boolean hasMom) {
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

        for (int t = 0; t < size; t++) {
            int lastDir = 0;
            int dir = 0;
            for (int j = 0; j < (size + 1) * (size + 1); j++) {
                if (j % 2 != 0) {
                    dir = lastDir;
                } else {
                    dir = new Random().nextInt(4);
                    lastDir = dir;
                }
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

                if (nextPoint[0] >= 0 && nextPoint[0] <= size && nextPoint[1] >= 0 && nextPoint[1] <= size && noInclude(rightPoints, nextPoint)) {
                    rightPoints.add(nextPoint);
                }
            }
            if (t % 2 == 0) {
                int[] pt = new int[]{0, new Random().nextInt(size + 1)};
                if (noInclude(rightPoints, pt)) {
                    rightPoints.add(pt);
                }
            } else {
                int[] pt = new int[]{new Random().nextInt(size + 1), 0};
                if (noInclude(rightPoints, pt)) {
                    rightPoints.add(pt);
                }
            }
        }
        map.setStartPoint(startPoint);
        map.setRightPoints(rightPoints);
        map.setSize(size);
        int monsterQuantity = (int) Math.floor(size * size / (new Random().nextInt(size) + 2));
        List<int[]> monsters = new ArrayList<>();
        for (int j = 0; j <= monsterQuantity; j++) {
            int[] monster = rightPoints.get(new Random().nextInt(rightPoints.size()));
            if (!Arrays.equals(monster, startPoint) && !aroundInclude(monsters, monster) && !oneEachSideInclude(rightPoints, monster)) {
                monsters.add(monster);
            }
        }
        map.setMonster(monsters);
        map.setMomPoints(new ArrayList<>());
        map.setFooPoints(new ArrayList<>());

        rightPoints.add(rightPoints.get((int) Math.floor(rightPoints.size() * 3 / 4)));

        return map;
    }

    public Response checkUserPathForBigMap(Map map, List<int[]> userPoints, String username) {
        Response response = new Response();
        response.setMap(map);
        response.setUserPoints(userPoints);
        response.setRightWay(true);
        response.setDieAtStep(userPoints.size() + 1);
        int step = 0;
        for (int[] point : userPoints) {
            step++;
            if (noInclude(map.getRightPoints(), point)) {
                response.setDieReason(DieReason.FALL);
                response.setDieAtStep(step);
                response.setRightWay(false);
                response.setStatus(Status.UNDONE);
                break;
            }
            if (!noInclude(map.getMonster(), point)) {
                response.setDieReason(DieReason.MONSTER);
                response.setDieAtStep(step);
                response.setRightWay(false);
                response.setStatus(Status.UNDONE);
                break;
            }
        }
        int[] lastPoint = userPoints.get(userPoints.size() - 1);
        if (response.isRightWay()) {
            if (Arrays.equals(lastPoint, map.getRightPoints().get(map.getRightPoints().size() - 1))) {
                response.setStatus(Status.DONE);
                response.setUnlockLevel(userRepository.increaseLevel(username));
            } else {
                response.setStatus(Status.UNDONE);
                response.getMap().setStartPoint(userPoints.get(userPoints.size() - 1));
            }
        }
        return response;
    }
}
