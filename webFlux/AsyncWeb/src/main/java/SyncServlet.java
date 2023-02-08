import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 直接使用webServlet注解，就不需要在web.xml中配置了
 */
@WebServlet("/SyncServlet")
public class SyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        doSomeThing(req, resp);
        long end = System.currentTimeMillis();
        System.out.println("耗时 ms : " + (end - start));
    }

    private void doSomeThing(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //响应
        resp.getWriter().append("done");
    }
}
