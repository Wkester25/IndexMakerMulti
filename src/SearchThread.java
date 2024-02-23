import java.util.ArrayList;

public class SearchThread implements Runnable{

    private final ArrayList<wordElement> result = SearchContainer.result;
    private int startIndex;
    private int increment;
    private ArrayList<String> list;
    public volatile boolean isWriting = false;

    public SearchThread(int startIndex, int increment, ArrayList<String> list) {
        this.startIndex = startIndex;
        this.increment = increment;
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < list.size(); i += increment) {
            addAllWords(list.get(i), i);
        }
    }

    public void addWord(String word, int num) {
        int index = foundOrInserted(word);
        if(index != -1) {
            result.get(index).addLocation(num);
            if(SearchContainer.mostFrequentWords.isEmpty() || result.get(index).getNumLocations() > SearchContainer.mostFrequentWords.get(0).getNumLocations()) {
                SearchContainer.mostFrequentWords.clear();
                SearchContainer.mostFrequentWords.add(result.get(index));
            }
            else if(result.get(index).getNumLocations() == SearchContainer.mostFrequentWords.get(0).getNumLocations()) {
                if(bianarySearch(word, SearchContainer.mostFrequentWords) == -1){
                    SearchContainer.mostFrequentWords.add(result.get(index));
                }
            }
            if(SearchContainer.leastFrequentWords.isEmpty() || result.get(index).getNumLocations() < SearchContainer.leastFrequentWords.get(0).getNumLocations()) {
                SearchContainer.leastFrequentWords.clear();
                SearchContainer.leastFrequentWords.add(result.get(index));
            }
            else if(result.get(index).getNumLocations() == SearchContainer.leastFrequentWords.get(0).getNumLocations()) {
                if(bianarySearch(word, SearchContainer.leastFrequentWords) == -1){
                    SearchContainer.leastFrequentWords.add(result.get(index));
                }
            }
        }
        SearchContainer.numWords++;
    }

    public void addAllWords(String str, int num) {
        String[] words = str.split("\\W+");
        for (String word : words) {
            if (!word.isEmpty()) {
                addWord(word, num);
                if(word.length() > SearchContainer.longestWord.length()) {
                    SearchContainer.longestWord = word;
                }
                if(word.length() < SearchContainer.shortestWord.length() || SearchContainer.shortestWord.isEmpty()) {
                    System.out.println(SearchContainer.shortestWord);
                    SearchContainer.shortestWord = word;
                }
            }
        }
    }

    private int foundOrInserted(String word) {
        if(word == null || word.isEmpty()) {
            return -1;
        }
        int location = bianarySearch(word);
        if(location != -1) {
            return location;
        }
        location = insertAlphabeticallyBinary(word);
        return location;
    }

    private int bianarySearch(String word) {
        int low = 0;
        int high = result.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compare = result.get(mid).getWord().compareToIgnoreCase(word);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }



    private int insertAlphabeticallyBinary(String word) {
        int low = 0;
        int high = result.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compare = result.get(mid).getWord().compareToIgnoreCase(word);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        SearchContainer.addIndex(new wordElement(word.toUpperCase()), low);
        return low;
    }

    private int bianarySearch(String word, ArrayList<wordElement> result) {
        int low = 0;
        int high = result.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compare = result.get(mid).getWord().compareToIgnoreCase(word);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }


}

