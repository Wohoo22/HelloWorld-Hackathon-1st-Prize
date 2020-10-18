package com.hackathon.service;

import com.hackathon.entity.Map;
import com.hackathon.entity.Response;
import com.hackathon.entity.User;

import java.util.List;

public interface MapService {
    Map generateMap(int size);

    Response checkUserPath(Map map, List<int[]> userPoints, String username);

    Map generateBigMap(int size, boolean hasMom);

    Response checkUserPathForBigMap(Map map, List<int[]> userPoints, String username);

}
