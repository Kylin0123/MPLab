package com.nju.mplab14;

import com.nju.mplab14.task5.Task5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Task5.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
