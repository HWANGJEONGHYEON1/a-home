package com.homework.demo.wish;

public class HistogramArea {

    public static int getMaxHistogramArea(int[] heights) {
        int maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            int minHeight = heights[i];

            for (int j = i; j < heights.length; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                int area = minHeight * (j - i + 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram1 = {2, 2, 2, 3};
        int maxArea1 = getMaxHistogramArea(histogram1);
        System.out.println("Maximum histogram area: " + maxArea1);  // Output: Maximum histogram area: 4

        int[] histogram2 = {6, 5, 7, 3, 4, 2};
        int maxArea2 = getMaxHistogramArea(histogram2);
        System.out.println("Maximum histogram area: " + maxArea2);  // Output: Maximum histogram area: 12
    }
}