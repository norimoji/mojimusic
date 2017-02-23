import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.graphviz.GraphvizGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import lombok.val;
import modules.JDABuilderModule;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Huy on 31/01/2017.
 */
@SuppressWarnings("all")
public final class Grapher {

    public static void main(String[] args) throws Exception {
        Grapher.graph(Guice.createInjector(new JDABuilderModule()));

        System.out.println("Graphed dependency tree at [" + getDateTime() + "]");
    }

    private static void graph(Injector targetInjector) throws IOException {
        val writer = new PrintWriter(new File("Dependency-Graph.dot"), "UTF-8");
        val injector = Guice.createInjector(new GraphvizModule());
        val grapher = injector.getInstance(GraphvizGrapher.class);

        grapher.setOut(writer);
        grapher.setRankdir("TB");
        grapher.graph(injector);
    }

    private static String getDateTime() {
        val dateTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.now().format(dateTimeFormat);
    }

}
