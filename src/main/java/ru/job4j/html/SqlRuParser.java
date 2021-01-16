package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.job4j.models.Post;
import ru.job4j.utils.SqlRuDateConvertor;

import java.io.IOException;
import java.util.Date;

public class SqlRuParser {
    private static final String BASE_URL = "https://www.sql.ru/forum/job-offers/";

    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 5; i++) {
//            Document doc = Jsoup.connect(BASE_URL + (i + 1)).get();
//            Elements posts = doc.select(".postslisttopic");
//            Elements updated = doc.select(".altCol:nth-child(even)");
//            for (int j = 0; j < posts.size(); j++) {
//                Element href = posts.get(j).child(0);
//                Element date = updated.get(j);
//                System.out.println(href.attr("href"));
//                System.out.println(href.text());
//                System.out.println(SqlRuDateConvertor.convertor(date.text()));
//            }
//        }
        Post post1 = getPost("https://www.sql.ru/forum/1332521/devops-inzhener-g-moskva");
        Post post2 = getPost("https://www.sql.ru/forum/1332490/trebuetsya-razrabotchik-sql");
        Post post3 = getPost("https://www.sql.ru/forum/1325330/"
                + "lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        System.out.println(post1.getText());
        System.out.println(post2.getText());
        System.out.println(post3.getText());
        System.out.println(post3.getTitle());
        System.out.println(post3.getUrl());
        System.out.println(post3.getText());
        System.out.println(post3.getDate());
    }

    public static Post getPost(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String header = doc.selectFirst(".messageHeader").text();
        String text = doc.selectFirst(".msgBody:nth-child(even)").text();
        String footer = doc.selectFirst(".msgFooter").text();
        String title = header.replace("[new]", "");
        Date date = SqlRuDateConvertor.convertor(footer);
        return new Post(url, title, text, date);
    }
}
