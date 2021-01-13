package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParser {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements posts = doc.select(".postslisttopic");
        Elements updated = doc.select(".altCol:nth-child(even)");

        for (int i = 0; i < posts.size(); i++) {
            Element href = posts.get(i).child(0);
            Element date = updated.get(i);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(date.text());
        }
    }
}
