package exercise;

import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class App {

    private static final List<Map<String, String>> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.get("/users", context -> {
            var page = context.queryParamAsClass("page",Integer.class).getOrDefault(1);
            var per = context.queryParamAsClass("per",Integer.class).getOrDefault(5);
            var users = Data.getUsers();
            var size = users.size();
            int offset = (int) ((page - 1) * per);
            int limit = (int) per;
            int endOffset = Math.min(offset + limit, size);
            context.json(users.subList(offset, endOffset));
        });

        return app;

    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
