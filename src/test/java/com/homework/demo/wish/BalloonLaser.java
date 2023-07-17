package com.homework.demo.wish;

import java.util.*;


public class BalloonLaser {

    public static int calculateMinimumShots(int[][] balloons) {
        Set<String> positions = new HashSet<>();
        Set<String> laserPositions = new HashSet<>();

        for (int[] balloon : balloons) {
            String position = balloon[0] + "," + balloon[1];
            positions.add(position);
        }

        for (String position : positions) {
            String[] parts = position.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            int gcd = gcd(Math.abs(x), Math.abs(y));
            int dx = x / gcd;
            int dy = y / gcd;

            String laserPosition = dx + "," + dy;
            laserPositions.add(laserPosition);
        }

        return positions.size() - laserPositions.size();
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int[][] balloons = {
                {2, 2},
                {4, 4},
                {1, 4},
                {-1, -4}
        };

        int minimumShots = calculateMinimumShots(balloons);
        System.out.println("Minimum shots required: " + minimumShots);  // Output: Minimum shots required: 3
    }
}



