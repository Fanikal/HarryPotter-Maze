package com.example.harrypottermaze;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class TranscriberDemo {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        URL modelBaseURL = TranscriberDemo.class.getResource("/edu/cmu/sphinx/models/en-us/");
        String modelBasePath = modelBaseURL.toExternalForm();

        configuration.setAcousticModelPath(modelBasePath + "en-us");
        configuration.setDictionaryPath(modelBasePath + "cmudict-en-us.dict");
        configuration.setLanguageModelPath(modelBasePath + "en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        //InputStream stream = new FileInputStream(new File("test.wav"));

        recognizer.startRecognition(true);
        SpeechResult rawResult = recognizer.getResult();
        if (rawResult != null ) {
            String hypothesis = rawResult.getHypothesis();
            System.out.println("Recognized: " + hypothesis);
        }

        /*
        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        SpeechResult result = recognizer.getResult();
        // Pause recognition process. It can be resumed then with startRecognition(false).
        recognizer.stopRecognition();

         */

        /*
        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();

         */
    }
}