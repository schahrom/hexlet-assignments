package exercise;

import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN

// END

public final class App {

    private static final List<Map<String, String>> COMPANIES = Data.getCompanies();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        app.get("/", ctx -> {
            ctx.result("open something like (you can change id): /companies/5");
        });

        app.get("/companies", ctx -> {
            ctx.json(COMPANIES);
        });

        app.get("/companies/{id}", context -> {
           var id = context.pathParamAsClass("id", Integer.class).get();
           var data = Data.getCompanies();
           if( id > data.size()) {
               context.status(404);
               context.result("Company not found");
           } else {
               Map<String, String> result = new HashMap<>();
               for (Map<String,String> company : data) {
                   if (Integer.parseInt(company.get("id")) == id) {
                       result = company;
                   }
               }
               context.json(result);
           }
        });

        return app;

    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
