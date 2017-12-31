package comics.service;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class RemoteURLReader {

    public String getComic(String url) throws IOException {
        System.setProperty("http.agent", "Chrome");
        StringBuilder stringBuilder = new StringBuilder();
        URL uri = new URL(url);
        URLConnection conn = uri.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        String imgURi = (String) jsonObject.get("img");
        reader.close();
        return imgURi;
    }
}