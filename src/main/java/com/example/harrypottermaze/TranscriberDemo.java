package com.example.harrypottermaze;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class TranscriberDemo {

    private Configuration configuration;
    private static LiveSpeechRecognizer recognizer;
    private static List<String> requiredWords;

    public TranscriberDemo() throws IOException {
        // Initialize configuration, recognizer, and requiredWords list in the constructor
        configuration = new Configuration();
        URL modelBaseURL = TranscriberDemo.class.getResource("/edu/cmu/sphinx/models/en-us/");
        String modelBasePath = modelBaseURL.toExternalForm();

        configuration.setAcousticModelPath(modelBasePath + "en-us");
        configuration.setDictionaryPath(modelBasePath + "cmudict-en-us.dict");
        configuration.setLanguageModelPath(modelBasePath + "en-us.lm.bin");

        recognizer = new LiveSpeechRecognizer(configuration);

        requiredWords = new ArrayList<>();
        requiredWords.add("solemnly");
        requiredWords.add("swear");
        requiredWords.add("i");
        requiredWords.add("that");
        requiredWords.add("up");
        requiredWords.add("to");
        requiredWords.add("no");
        requiredWords.add("good");
    }

    public static boolean recognizeOpenMap() {
        // Start recognition
        recognizer.startRecognition(true);
        SpeechResult rawResult = recognizer.getResult();

        // Stop recognition
        recognizer.stopRecognition();

        if (rawResult != null) {
            String hypothesis = rawResult.getHypothesis();
            String[] recognizedWords = hypothesis.split("\\s+");
            int recognizedWordsCount = 0;
            for (String recognizedWord : recognizedWords) {
                if (requiredWords.contains(recognizedWord.toLowerCase())) {
                    recognizedWordsCount++;
                }
            }

            if (recognizedWordsCount >= 2) {
                // The phrase was correct and the game will open
                System.out.println("Opening the game!");
                return true;
            }
            System.out.println("Recognized: " + hypothesis);
        }

        return false; // the phrase was not recognized
    }
}
