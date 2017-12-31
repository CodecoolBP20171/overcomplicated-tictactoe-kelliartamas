package comics.controller;


import comics.model.Comics;
import comics.service.RemoteURLReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

@RestController
public class ComicsController {

    @Autowired
    private RemoteURLReader remoteURLReader;

    @GetMapping("/comics")
    public Comics getComics() throws IOException {
        Random random = new Random();
        int rnd = random.nextInt(1928) + 1;
        String uri = "https://xkcd.com/" + rnd + "/info.0.json";
        String parsedURL = remoteURLReader.getComic(uri);
        return new Comics(parsedURL, rnd);
    }
}
