import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class MyReadListener implements ReadListener {

    private ServletInputStream in;
    private AsyncContext ac;
    public MyReadListener(ServletInputStream input,AsyncContext context){
        this.in = input;
        this.ac = context;
    }
    //数据可用时触发执行
    @Override
    public void onDataAvailable(){
        try {
            System.out.println("模拟处理数据");
            Thread.sleep(2000);//暂停5秒，模拟耗时处理数据
            //读取in里的数据省略...
            ac.getRequest().setAttribute("username", "peter");//耗时处理后返回的数据
            System.out.println("模拟处理数据, 完毕, 开始跳转");
            ac.dispatch("/index.jsp");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //数据读完时触发调用
    @Override
    public void onAllDataRead(){
        System.out.println("数据读完了");
    }

    //数据出错触发调用
    @Override
    public void onError(Throwable t){
        System.out.println("数据 出错");
        t.printStackTrace();
    }
}