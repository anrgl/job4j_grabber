package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDateConvertor;

import java.io.IOException;

public class SqlRuParser {
    private static final String BASE_URL = "https://www.sql.ru/forum/job-offers/";

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) {
            Document doc = Jsoup.connect(BASE_URL + (i + 1)).get();
            Elements posts = doc.select(".postslisttopic");
            Elements updated = doc.select(".altCol:nth-child(even)");
            for (int j = 0; j < posts.size(); j++) {
                Element href = posts.get(j).child(0);
                Element date = updated.get(j);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(SqlRuDateConvertor.convertor(date.text()));
            }
        }

    }
}
