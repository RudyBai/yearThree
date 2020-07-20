package PartTwo;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class HandleSession extends Thread {

    private Socket client;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private int clientNumber;
    private ArrayList<Integer> history;

    HandleSession(Socket client, int clientNumber) {
        this.client = client;
        this.clientNumber = clientNumber;
        this.history = new ArrayList<>();

        this.questions = this.getQuestions();
        this.answers = this.getAnswers();
    }

    @Override
    public void run() {
        try {
            DataInputStream fromClient = new DataInputStream(client.getInputStream());
            DataOutputStream toClient = new DataOutputStream(client.getOutputStream());

            String request, response;
            int responseIndex;

            //noinspection InfiniteLoopStatement
            while (true) {
                request = fromClient.readUTF();
                responseIndex = processRequest(request);
                if (responseIndex == 5) {
                    int mostPop = 0;
                    int mostPopIndex = 0;
                    HashMap<Integer, Integer> occurrences = this.countOccurrences(history);
                    for (int i = 0; i < occurrences.size(); i++) {
                        if (occurrences.get(i) > mostPop) {
                            mostPop = occurrences.get(i);
                            mostPopIndex = i;
                        }
                    }

                    toClient.writeUTF("Most popular question is: " + questions.get(mostPopIndex));

                } else {
                    if (responseIndex == 0) {
                        response = String.valueOf(new Date());
                    } else {
                        response = answers.get(responseIndex);
                    }
                    history.add(responseIndex);
                    toClient.writeUTF(response);

                }

                toClient.flush();

            }
        } catch (SocketException e) {
            System.out.println("<SERVER> Client number " + clientNumber + " disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int processRequest(String request) {

        if (request.startsWith("Bob, pop")) {
            return 5;
        }

        int mostSimilarIndex = 0;
        float mostSimilarValue = 0;

        for (int i = 0; i < this.questions.size(); i++) {
            if (this.stringSimilarity(request, this.questions.get(i)) > mostSimilarValue) {
                mostSimilarIndex = i;
                mostSimilarValue = this.stringSimilarity(request, this.questions.get(i));
            }
        }
        return mostSimilarIndex;
    }

    float stringSimilarity(String request, String question) {

        float similarity = 0;

        ArrayList<String> questionArray = new ArrayList<>(Arrays.asList(question.split(" ")));
        ArrayList<String> answerArray = new ArrayList<>(Arrays.asList(request.split(" ")));

        for (String wordInRequest : answerArray) {
            for (String wordInQuestion : questionArray) {
                if (wordInRequest.toUpperCase().equals(wordInQuestion.toUpperCase())) {
                    similarity++;
                }
            }
        }

        return similarity;

    }

    private HashMap<Integer, Integer> countOccurrences(ArrayList<Integer> integerArrayList) {
        ArrayList<Integer> unique = new ArrayList<>();
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        for (Integer i : integerArrayList) {
            if (!unique.contains(i)) {
                unique.add(i);
            }
        }
        for (Integer i : unique) {
            occurrences.put(i, 0);
            for (Integer j : integerArrayList) {
                if (i.equals(j)) {
                    occurrences.put(i, occurrences.get(i) + 1);
                }
            }
        }
        return occurrences;
    }

    private ArrayList<String> getQuestions() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\walus\\OneDrive - mycit.ie\\Year Three\\DSP2\\src\\questions.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (ArrayList<String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> getAnswers() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\walus\\OneDrive - mycit.ie\\Year Three\\DSP2\\src\\answers.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (ArrayList<String>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
