package com.apys.learning.dao;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DBInit {
    public static void startIp() throws Exception {
        URL resource1 = DictionaryDAOImplTest.class.getClassLoader().getResource("student-project.sql");
        URL resource2 = DictionaryDAOImplTest.class.getClassLoader().getResource("student_data.sql");

        List<String> strings1 = Files.readAllLines(Paths.get(resource1.toURI()));
        String sql1 = strings1.stream().collect(Collectors.joining());

        List<String> strings2 = Files.readAllLines(Paths.get(resource2.toURI()));
        String sql2 = strings2.stream().collect(Collectors.joining());

        try(Connection connection = ConnectionBuilder.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }
    }
}
