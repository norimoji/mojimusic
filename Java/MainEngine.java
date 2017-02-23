import bot.JDABuilderImpl;
import lombok.val;
import modules.JDABuilderModule;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.Injector.INJECTOR;
import static net.dv8tion.jda.core.entities.Game.of;

/**
 * Created by Huy on 21/01/2017.
 */
@SuppressWarnings("unused")
public final class MainEngine extends HttpServlet {

    public static boolean run = false;

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new MainEngine()),"/*");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start() throws LoginException, RateLimitedException, InterruptedException {
        val bot = INJECTOR.getInstance(new JDABuilderModule(), JDABuilderImpl.class).buildBlocking();
        bot.getPresence().setGame(of("Development In Progress"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
        if(!run){
            try {
                this.start();
            } catch (LoginException e) {
                e.printStackTrace();
            } catch (RateLimitedException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run=true;
        }
    }
}
