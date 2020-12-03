import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.ResponseTransformer;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;



public class App {


    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        staticFiles.location("/public");


        String dbDiskURL = "jdbc:postgresql://localhost:5432/chocolate_list";

        Jdbi jdbi = Jdbi.create(dbDiskURL, "codex", "codex123");

        // get a handle to the database
        Handle handle = jdbi.open();
        List<String> chocList = new ArrayList<String>();
//        get("/chocShop", (req, res) -> "Hello!");


//List<String> names = handle.createQuery("select name from greet")
//                        .mapTo(String.class)
//                        .list();

        get("/chocShop", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
            List <String> chocNames = handle.createQuery("select name from chocolate").mapTo(String.class).list();

            map.put("chocList",chocNames);
            return new ModelAndView(map, "index.handlebars");

        }, new HandlebarsTemplateEngine());

        post("/chocShop", (req, res) -> {

            Map<String, Object> map = new HashMap<>();
         String chocName = req.queryParams("chocName");
         int qty = Integer.parseInt(req.queryParams("qty"));

            System.out.println(chocName +""+ qty);
//         handle.execute("insert into greet (name) values (?)", username);
            handle.execute("insert into chocolate(name,qty) values (?,?)", chocName,qty);
//            handle.execute("insert into chocolate(qty) values (?)", qty);

            // put it in the map which is passed to the template - the value will be merged into the template
//            map.put("bought", bought);
            res.redirect("/chocShop");
            return new ModelAndView(map, "index.handlebars");

        }, new HandlebarsTemplateEngine());

        get("/chocShop/delete",(res,req)->{
            Map<String, Object> map = new HashMap<>();

            List <String> chocNames = handle.createQuery("select name from chocolate").mapTo(String.class).list();

            map.put("chocList",chocNames);
//         handle.execute("delete from chocolate(name,qty) values (?,?)", chocName,qty);

            return new ModelAndView(map, "deleteChoc.handlebars");
        },new HandlebarsTemplateEngine());


        post("/chocShop/delete",(res,req)->{
            Map<String, Object> map = new HashMap<>();
            String chocName = res.queryParams("chocolate");

//            List <String> chocNames = handle.createQuery("delete name from chocolate").mapTo(String.class).list();

//            map.put("chocList",chocNames);
        handle.execute("delete from chocolate where name=?", chocName);

        req.redirect("/chocShop");

            return new ModelAndView(map, "deleteChoc.handlebars");
        },new HandlebarsTemplateEngine());
    }




    static int getHerokuAssignedPort(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}





//class ChocCounts {
//
//
//
//}