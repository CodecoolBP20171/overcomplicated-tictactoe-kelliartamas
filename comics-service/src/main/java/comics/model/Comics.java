package comics.model;

import java.util.Random;

public class Comics {

    private String uri;
    private int randomNum;

    public Comics() {
    }

    public Comics(String uri, int randomNum) {
        this.uri = uri;
        this.randomNum = randomNum;
    }

    public String getUri() {
        return uri;
    }
}
