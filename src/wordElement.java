import java.util.ArrayList;

public class wordElement {
    public String word;
    private ArrayList<Integer> locations;

    public wordElement(String word, int location) {
        this.word = word;
        this.locations = new ArrayList<Integer>();
        this.locations.add(location);
    }

    public wordElement(String word) {
        this.word = word;
        this.locations = new ArrayList<Integer>();
    }

    public void addLocation(int location) {
        this.locations.add(location);
    }

    public String getWord() {
        return this.word;
    }

    public ArrayList<Integer> getLocations() {
        return this.locations;
    }

    public String toString() {
        return this.word + ": " + this.locations;
    }

    public boolean equals(Object obj) {
            wordElement e = (wordElement) obj;
            return this.word.equals(e.word);
    }

}
