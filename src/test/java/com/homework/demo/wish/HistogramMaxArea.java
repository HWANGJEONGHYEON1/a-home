package com.homework.demo.wish;

import java.util.Stack;

public class HistogramMaxArea {
    public static int maxWaterArea(int[] histogram) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int currentIndex = 0;

        while (currentIndex < histogram.length) {
            while (!stack.isEmpty() && histogram[currentIndex] >= histogram[stack.peek()]) {
                int topIndex = stack.pop();

                if (!stack.isEmpty()) {
                    int width = currentIndex - stack.peek() - 1;
                    int height = Math.min(histogram[currentIndex], histogram[stack.peek()]) - histogram[topIndex];
                    int area = width * height;
                    maxArea = Math.max(maxArea, area);
                }
            }

            stack.push(currentIndex);
            currentIndex++;
        }

        while (!stack.isEmpty()) {
            int topIndex = stack.pop();

            if (!stack.isEmpty()) {
                int width = currentIndex - stack.peek() - 1;
                int height = Math.min(histogram[currentIndex], histogram[stack.peek()]) - histogram[topIndex];
                int area = width * height;
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram = {2, 2, 2, 3};
        int maxArea = maxWaterArea(histogram);
        System.out.println("Maximum water area: " + maxArea);
    }
}