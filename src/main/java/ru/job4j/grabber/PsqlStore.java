package ru.job4j.grabber;

import ru.job4j.grabber.models.Post;
import ru.job4j.grabber.utils.SqlRuDateConvertor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private final Connection cn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.login"),
                    cfg.getProperty("jdbc.password"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cn.prepareStatement(
                "insert into job(name, text, link, created) values (?, ?, ?, ?)")) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getText());
            statement.setString(3, post.getUrl());
            statement.setDate(4, new Date(post.getDate().getTime()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (Statement statement = cn.createStatement()) {
            try (ResultSet result = statement.executeQuery("select * from job")) {
                while (result.next()) {
                    posts.add(new Post(
                            result.getInt("id"),
                            result.getString("link"),
                            result.getString("name"),
                            result.getString("text"),
                            result.getDate("created")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = new Post();
        try (PreparedStatement statement =
                     cn.prepareStatement("select * from job where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    post.setId(result.getInt(1));
                    post.setTitle(result.getString(2));
                    post.setText(result.getString(3));
                    post.setUrl(result.getString(4));
                    post.setDate(result.getDate(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    public static void main(String[] args) {
        Properties cfg = new Properties();
        try (InputStream in = ClassLoader
                .getSystemClassLoader().getResourceAsStream("app.properties")) {
            cfg.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PsqlStore psqlStore = new PsqlStore(cfg);
        Post firstPost = new Post(
                "https://www.sql.ru/forum/1332314/trebuetsya-java-razrabotchik-v-bank-top-3",
                "Требуется Java разработчик в банк топ 3",
                "Москва\n"
                        + "Зп до 320000 гросс\n\n"
                        + "Java разработчик\n\n"
                        + "Требования к кандидатам:\n\n"
                        + "• высшее техническое образование;\n"
                        + "• опыт работы Java разработчиком от 3 лет "
                        + "(опыт работы в банковской сфере будет преимуществом);\n"
                        + "• базовые знания банковской сферы (риски);\n"
                        + "• наличие опыта работы с PostgreSQL "
                        + "(разработка и оптимизация процедур);\n"
                        + "• знание практик DevOps;\n"
                        + "• знание OpenShift, Docker;\n"
                        + "• опыт работы с Hadoop (Impala, Hive, Spark);\n"
                        + "• быстрое погружение в проект;\n"
                        + "• умение работать на результат, способность работать в команде;\n"
                        + "• знание и умение работы с пакетом Confluence, Jira;\n"
                        + "• хорошие коммуникативные навыки;\n"
                        + "• знание основ Agile.\n\n"
                        + "Задачи:\n"
                        + "• BATCH-интеграция с источниками;\n"
                        + "• интеграция с источниками через REST API;\n"
                        + "• формирование расчетного слоя (разработка процедур "
                        + "расчета рыночного риска);\n"
                        + "• разработка витрин;\n"
                        + "• разработка аналитического модуля.\n\n"
                        + "Подробности:\n"
                        + "@Han_Evgeniy\n"
                        + "hrcompany1987@gmail.com",
                SqlRuDateConvertor.convertor("6 янв 21, 15:33"));
        psqlStore.save(firstPost);
        List<Post> posts = psqlStore.getAll();
        for (Post post : posts) {
            System.out.println(post.getTitle());
        }
        Post postById = psqlStore.findById(1);
        System.out.println(postById.getId());
        System.out.println(postById.getUrl());
        System.out.println(postById.getDate());
    }
}
