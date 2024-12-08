import com.hyperionml.utils.impl.LinkedListQueue;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Scanner;

public class QueueTest {

    @Test
    public void offer(){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.iterator().forEachRemaining(System.out::println);
    }

    @Test
    public void peek(){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        System.out.println(queue.peek());
        queue.offer(1);
        System.out.println(queue.peek());
        queue.offer(2);
        System.out.println(queue.peek());
    }

    @Test
    public void poll(){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    @Test
    public void offerLimit(){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>(3);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        System.out.println(queue.offer(4));
        System.out.println(queue.offer(5));

        queue.iterator().forEachRemaining(System.out::println);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        System.out.println("程序已启动，打印机默认处于休眠状态（1.需要启动打印程序请输入 /start ， 2.需要关闭程序请输入 /stop）");
        while (true){
            System.out.println("请输入需要打印的内容");
            String in = input.nextLine();
            if("/start".equals(in)){
                System.out.println("--------------------------打印开始-------------------------");
                queue.iterator().forEachRemaining(s -> {
                    Long start = System.currentTimeMillis();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(s);
                    Long end = System.currentTimeMillis();
                    System.out.println("本次打印任务用时为：" + (end - start)/1000.0 + "秒, 打印开始时间为：" + LocalTime.now());
                });
                queue = new LinkedListQueue<>();
                System.out.println("--------------------------本次打印结束(打印队列已初始化)-------------------------");
            }
            else if("/stop".equals(in)){
                break;
            }
            else {
                queue.offer(in);
            }
        }
    }
}
