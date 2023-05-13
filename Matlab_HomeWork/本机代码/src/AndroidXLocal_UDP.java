import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class AndroidXLocal_UDP {

    static String targetIp = "192.168.2.146";//Android手机IP 内网
    public static void main(String[] args) {
        new Service().start();
    }


    static class Service extends Thread{

        DatagramPacket packet;
        DatagramSocket socket;
        int totalSize = 1024 * 1024 * 256;
        int blockSize = 1024;
        byte[] totalData = new byte[totalSize];
        byte[] blockData = new byte[blockSize];
        BufferedInputStream bis = null;
        int readSize;
        int sendCount = 0;
        @Override
        public void run() {
            System.out.println("总发送："+totalSize/blockSize);
            Arrays.fill(totalData,(byte)9);
            try {
                bis = new BufferedInputStream(new ByteArrayInputStream(totalData));
                socket = new DatagramSocket();
                long begin = System.currentTimeMillis();
                while((readSize = bis.read(blockData)) != 0 && readSize != -1){
                    packet = new DatagramPacket(blockData,readSize, InetAddress.getByName(targetIp),1234);
                    socket.send(packet);
                    sendCount++;
                }
                socket.send(new DatagramPacket(new byte[]{1},1,InetAddress.getByName(targetIp),1234));
                long end = System.currentTimeMillis();
                System.out.println("发送完毕+用时："+(end-begin)+"ms");
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                socket.close();
            }
        }
    }
}


