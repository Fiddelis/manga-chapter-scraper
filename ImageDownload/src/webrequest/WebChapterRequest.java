package webrequest;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlRequest {

    public String ChapterRequest(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try {
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content;
            StringBuilder html = new StringBuilder();

            while ((content = reader.readLine()) != null) {
                html.append(content);
            }
            reader.close();
            return regex(html);

        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    private String regex(StringBuilder html) {
        Pattern p = Pattern.compile("(https:\\\\/\\\\/)+(\\S)+(.jpg)");
        Matcher m = p.matcher(html);
        if (m.find()) {
            return m.group().replace("\\", "");
        } else {
            return "not found";
        }
    }
}