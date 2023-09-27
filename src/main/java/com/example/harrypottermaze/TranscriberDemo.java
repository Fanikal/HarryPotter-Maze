package com.example.harrypottermaze;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class TranscriberDemo {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath(TranscriberDemo.class.getResource("/edu/cmu/sphinx/models/en-us").getPath());
        configuration.setDictionaryPath(TranscriberDemo.class.getResource("/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict").getPath());
        configuration.setLanguageModelPath(TranscriberDemo.class.getResource("/edu/cmu/sphinx/models/en-us/en-us.lm.bin").getPath());

        /*StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("test.wav"));


               */

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
// Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        SpeechResult result = recognizer.getResult();
// Pause recognition process. It can be resumed then with startRecognition(false).
        recognizer.stopRecognition();

        /*recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();

         */
    }
}