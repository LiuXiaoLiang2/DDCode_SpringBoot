import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 直接使用webServlet注解，就不需要在web.xml中配置了
 */
@WebServlet(value = "/ASyncServlet", asyncSupported = true)
public class ASyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //开启异步
        AsyncContext asyncContext = req.startAsync();
        long start = System.currentTimeMillis();
        //将操作放到异步线程池
        CompletableFuture.runAsync(
                ()->{
                    try {
                        doSomeThing(asyncContext);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        long end = System.currentTimeMillis();
        System.out.println("耗时 ms : " + (end - start));
    }

    private void doSomeThing(AsyncContext asyncContext) throws IOException {
        ServletRequest request = asyncContext.getRequest();
        ServletResponse response = asyncContext.getResponse();
        //模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //响应
        response.getWriter().append("done");
        //通知resp已经处理完了
        asyncContext.complete();
    }
}
