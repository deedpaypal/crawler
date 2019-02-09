package crawler;

public class Main {

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.start("https://www.consumerreports.org/cro/index.htm");
    }
}
