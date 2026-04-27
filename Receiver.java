import java.net.*; 
import java.util.Random; 

public class Receiver {
    private static final int PORT = 9876;
    private static final double LOSS_PROBABILITY = 0.3;  

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Receiver started, waiting for frames...");
            
            byte[] receiveBuffer = new byte[1024];
            int expectedSeqNum = 0;
            Random random = new Random();

            while (true) {
                // Receive Frame 
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                // Simple frame format: "SEQ_NUM:DATA"
                String[] parts = message.split(":", 2);
                int seqNum = Integer.parseInt(parts[0]);
                String data = parts[1];

                System.out.println("\n[RECEIVER] Received: Frame " + seqNum + " with data: '" + data + "'");

                // Check for sequence number (Stop-and-Wait uses 0 and 1)
                if (seqNum == expectedSeqNum) {
                    System.out.println("[RECEIVER] Frame " + seqNum + " is correct. Processing...");
                    expectedSeqNum = 1 - expectedSeqNum; // Toggle expected sequence number
                } else {
                    System.out.println("[RECEIVER] Duplicate Frame " + seqNum + " detected (ACK was likely lost).");
                }

                // Simulate ACK loss
                if (random.nextDouble() < LOSS_PROBABILITY) {
                    System.out.println("[RECEIVER] *** SIMULATED LOSS: ACK for Frame " + seqNum + " dropped! ***");
                    continue; // Skip sending ACK
                }

                // Send ACK
                String ackMessage = "ACK:" + seqNum;
                byte[] ackData = ackMessage.getBytes();
                InetAddress senderAddress = receivePacket.getAddress();
                int senderPort = receivePacket.getPort();
                
                DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, senderAddress, senderPort);
                socket.send(ackPacket);
                System.out.println("[RECEIVER] Sent: ACK for Frame " + seqNum);
            }
        } catch (Exception e) {
            System.err.println("Receiver error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
