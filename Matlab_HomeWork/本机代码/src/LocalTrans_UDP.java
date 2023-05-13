import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.*;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class LocalTrans_UDP {

    static ReentrantLock Sendlock;
    static ReentrantLock RecieveLock;
    static Condition Sendcondition;
    static String LocalIp;
    static volatile boolean isFinish = false;
    //初始化本地IP
    static{
        try {
            LocalIp = InetAddress.getLocalHost().getHostAddress();
            System.out.println(LocalIp);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException, UnknownHostException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //本机UDP通信 - 不进行同步
        Sendlock = new ReentrantLock(true);
        RecieveLock = new ReentrantLock(true);
        Sendcondition = Sendlock.newCondition();
        Future<Map.Entry<Long,Long>> end = executor.submit(new SendThread());
        Future<Long> start = executor.submit(new ReceiveThread());
        Long result = end.get().getValue() - end.get().getKey();
        //System.out.println("耗时："+result+"ms");
        executor.shutdown();
        return ;
    }

    public static void initTestBytes(byte[] data){
        Arrays.fill(data, (byte) 9);
    }

    static class SendThread implements Callable<Map.Entry<Long,Long>> {
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        BufferedInputStream bis = null;
        Long startTime = null;
        Long endTime = null;
        int blockSize = 1024;
        int totalSize = 1024 * 1024 * 256;
        int readSize;
        int sendConut = 0;
        public void run() {
            byte[] data = new byte[totalSize];
            initTestBytes(data);
            byte[] blockData = new byte[blockSize];
            try {

                //准备就绪，进入等待状态
                Sendlock.lock();
                socket = new DatagramSocket();//创建Socket
                bis = new BufferedInputStream(new ByteArrayInputStream(data));//将一个字节数组包装成一个输入流

                Sendcondition.signalAll();System.out.println("发送线程唤醒1");
                System.out.println("发送线程wait");Sendcondition.await();
                Sendcondition.signalAll();System.out.println("发送线程唤醒2");
                Sendlock.unlock();

                startTime = System.currentTimeMillis();
                while((readSize = bis.read(blockData)) != 0 && readSize != -1){
                    //System.out.println("readSize"+readSize);
                    packet = new DatagramPacket(blockData,readSize,InetAddress.getByName(LocalIp),666);
                    socket.send(packet);
                    sendConut++;
                }
                endTime = System.currentTimeMillis();
                System.out.println("全部发送完毕,发送数据包数："+sendConut+" 用时:"+(endTime-startTime)+"ms");
                isFinish = true;
                socket.send(packet);
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();

            }
        }

        @Override
        public Map.Entry<Long,Long> call() throws Exception {
            run();
            endTime = System.currentTimeMillis();
            return new AbstractMap.SimpleEntry<>(startTime,endTime);
        }
    }
    static class ReceiveThread implements Callable<Long>{
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        int receiveCount = 0;
        public void run() {
            try{
                socket = new DatagramSocket(666);//创建端口号
                packet = new DatagramPacket(new byte[1024],1024);//创建数据包

                Sendlock.lock();
                Sendcondition.signal();System.out.println("接收线程唤醒1");
                System.out.println("接收线程等待");Sendcondition.await();
                Sendcondition.signal();System.out.println("接收线程唤醒2");
                Sendlock.unlock();

                while(!isFinish){
                    socket.receive(packet);
                    byte[] arr = packet.getData();
                    int len = packet.getLength();
                    receiveCount++;
                    //System.out.println("接收数:"+(receiveCount-1));
                }

                System.out.println("RecieveCounter:"+(receiveCount-1));
                System.out.println("接收完毕");

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                socket.close();

            }
          }

        @Override
        public Long call() throws Exception {
            run();
            return System.currentTimeMillis();
        }
     }

     //采取同步机制时的UDPdemo
    static class SendThread_Syn implements Callable<Map.Entry<Long,Long>> {
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        BufferedInputStream bis = null;
        Long startTime = null;
        Long endTime = null;
        int blockSize = 1024;
        int totalSize = 1024 * 512;
        int readSize;
        int sendConut = 0;
        public void run() {
            byte[] data = new byte[totalSize];
            initTestBytes(data);
            byte[] blockData = new byte[blockSize];
            try {

                //准备就绪，进入等待状态
                Sendlock.lock();
                socket = new DatagramSocket();//创建Socket
                bis = new BufferedInputStream(new ByteArrayInputStream(data));//将一个字节数组包装成一个输入流

                Sendcondition.signalAll();System.out.println("发送线程唤醒1");
                System.out.println("发送线程wait");Sendcondition.await();
                Sendcondition.signalAll();System.out.println("发送线程唤醒2");
                Sendlock.unlock();

                startTime = System.currentTimeMillis();
                while((readSize = bis.read(blockData)) != 0 && readSize != -1){
                    //System.out.println("readSize"+readSize);
                    packet = new DatagramPacket(blockData,readSize,InetAddress.getByName(LocalIp),666);
                    socket.send(packet);
                    sendConut++;
                }
                System.out.println("全部发送完毕,发送数据包数："+sendConut);

                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();
                isFinish = true;
            }
        }

        @Override
        public Map.Entry<Long,Long> call() throws Exception {
            run();
            endTime = System.currentTimeMillis();
            return new AbstractMap.SimpleEntry<>(startTime,endTime);
            }
        }

    static class ReceiveThread_Syn implements Callable<Long>{
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        int receiveCount = 0;
        public void run() {
            try{
                socket = new DatagramSocket(666);//创建端口号
                packet = new DatagramPacket(new byte[1024],1024);//创建数据包

                Sendlock.lock();
                Sendcondition.signal();System.out.println("接收线程唤醒1");
                System.out.println("接收线程等待");Sendcondition.await();
                Sendcondition.signal();System.out.println("接收线程唤醒2");
                Sendlock.unlock();

                while(!isFinish){
                    socket.receive(packet);
                    byte[] arr = packet.getData();
                    int len = packet.getLength();
                    receiveCount++;
                    System.out.println("接收数:"+receiveCount);
                }

                System.out.println("RecieveCounter:"+receiveCount);
                System.out.println("接收完毕");

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                socket.close();

            }
        }

        @Override
        public Long call() throws Exception {
            run();
            return System.currentTimeMillis();
        }
    }
    }

