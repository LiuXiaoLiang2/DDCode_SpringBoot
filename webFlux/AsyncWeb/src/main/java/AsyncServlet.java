import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="AsyncServlet",urlPatterns={"/testAsyn.do"},asyncSupported=true)
public class AsyncServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //解决乱码
        request.setCharacterEncoding("GBK");
        response.setContentType("text/html;charset=GBK");
        //通过request获得AsyncContent对象
        AsyncContext actx = request.startAsync();
        //设置异步调用超时时长
        actx.setTimeout(30*3000);

        ServletInputStream in = request.getInputStream();
        //异步读取（实现了非阻塞式读取）
        in.setReadListener(new MyReadListener(in,actx));
        // 直接输出到页面的内容(不等异步完成就直接给页面)
        //但这些内容必须放在标签内,否则会在页面输出错误内容，这儿反正我测试是这样，具体不知对不对？？
        PrintWriter out = response.getWriter();
        out.println("<h3>1111不等异步返回结果就直接返到页面的内容</h3>");
        out.flush();
    }
}