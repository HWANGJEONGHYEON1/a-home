package com.homework.demo.wish;

import java.util.*;

public class Histogram {
    public static int maxWaterArea(int[] histogram) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        int i = 0;

        while (i < histogram.length) {
            if (stack.isEmpty() || histogram[i] >= histogram[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                int top = stack.pop();
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                int area = histogram[top] * width;
                maxArea = Math.max(maxArea, area);
            }
        }

        while (!stack.isEmpty()) {
            int top = stack.pop();
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            int area = histogram[top] * width;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram = {6, 5, 7, 3, 4, 2};
        int maxArea = maxWaterArea(histogram);
        System.out.println("Maximum water area: " + maxArea);
    }
}