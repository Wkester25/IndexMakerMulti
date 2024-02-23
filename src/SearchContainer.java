import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class SearchContainer {
    public static volatile DocumentIndex result = new DocumentIndex(575000);
    public static volatile int numWords = 0;
    public static volatile ArrayList<Thread> threads = new ArrayList<Thread>();
    public static volatile String shortestWord = "";
    public static volatile String longestWord = "";
    public static volatile int numIndexes = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\nEnter input file name: ");
        String fileName = keyboard.nextLine().trim();
        File file = new File(fileName);
        System.out.print("\nEnter output file name: ");
        String outputFileName = keyboard.nextLine().trim();
        Scanner scanner = new Scanner(file);
        PrintWriter outputFile = new PrintWriter(new FileWriter(outputFileName));
        long startTime = System.currentTimeMillis();
        ArrayList<String> list = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        int numThreads = Runtime.getRuntime().availableProcessors();
        startThreads(numThreads, list);
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        outputFile.println("Input file: " + fileName);
        outputFile.println("Output file: " + outputFileName);
        outputFile.println("Number of words: " + numWords);
        outputFile.println("Number of indexes: " + numIndexes);
        outputFile.println("Shortest word: " + shortestWord);
        outputFile.println("Longest word: " + longestWord);
        outputFile.println("Time: " + (endTime - startTime) + "ms");
        outputFile.println("Number of threads: " + numThreads);
        for (wordElement word : result) {
            outputFile.println(word);
        }
        outputFile.close();
        System.out.println("number of words: " + numWords);

    }

    public static void startThreads(int numThreads, ArrayList<String> list) throws InterruptedException {
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new SearchThread(i, numThreads, list));
            threads.add(thread);
            thread.start();
            thread.join();
        }

    }

    public static void addIndex(wordElement word, int location) {
        result.add(location, word);
        numIndexes++;
    }
}
