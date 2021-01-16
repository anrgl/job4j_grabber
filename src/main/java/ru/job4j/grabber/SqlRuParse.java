package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.models.Post;
import ru.job4j.utils.SqlRuDateConvertor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlRuParse implements Parse {
    @Override
    public List<Post> list(String link) {
        List<Post> postsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link + (i + 1)).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements posts = doc.select(".postslisttopic");
            for (Element post : posts) {
                String href = post.child(0).attr("href");
                postsList.add(detail(href));
            }
        }
        return postsList;
    }

    @Override
    public Post detail(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.selectFirst(".messageHeader").text().replace("[new]", "");
        String text = doc.selectFirst(".msgBody:nth-child(even)").text();
        Date date = SqlRuDateConvertor.convertor(
                doc.selectFirst(".msgFooter").text());
        return new Post(link, title, text, date);
    }
}
