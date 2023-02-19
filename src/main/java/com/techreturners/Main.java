package com.techreturners;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        Topics topics = new Topics();
        Topics.getTopics();
    }
}