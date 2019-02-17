package crawler;

import helpers.FileHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    static List<String> linksList = new ArrayList<>();
    static List<String> visitedLinks = new ArrayList<>();

    public void start(String url) {
        Document doc;
        Elements links = new Elements();
        FileHelper helper = new FileHelper();
        try {
            doc = Jsoup.connect(url).get();
            links = doc.select("a[href]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element link : links) {
            String href = link.attr("href");
            if (href.contains("www.consumerreports.org") && !href.startsWith("//")) {
                System.out.println(href);
                if (!linksList.contains(href)) {
                    linksList.add(href);
                }

            }
        }

        visitedLinks.add(url);

        if (linksList.size() < 100) {
            start(linksList.get(visitedLinks.indexOf(url) + 1));
        } else {
            helper.writeToIndexTXT(linksList);
            for (int i = 0; i < 100; i++) {
                helper.writeToFile(helper.createFileBodiesPath(i), getBodyFromURL(linksList.get(i)));
            }
        }

    }

    public String getBodyFromURL(String url) {
        String body = "";
        try {
            body = Jsoup.connect(url).timeout(10 * 1000).get().body().text().replaceAll("\n", " ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

}
