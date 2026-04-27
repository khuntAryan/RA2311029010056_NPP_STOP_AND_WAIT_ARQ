import java.net.*;


public class Sender {
    private static final String RECEIVER_ADDRESS = "localhost";
    private static final int RECEIVER_PORT = 9876;
    private static final int TIMEOUT = 2000; // 2 seconds
    private static final int TOTAL_FRAMES = 5;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT);
            // converting local address to ip
            InetAddress receiverAddr = InetAddress.getByName(RECEIVER_ADDRESS);
            
            int currentSeqNum = 0;
            String[] dataToSend = {"Hello", "World", "Java", "Socket", "ARQ"};

            for (int i = 0; i < TOTAL_FRAMES; i++) {
                String frameData = dataToSend[i];
                boolean ackReceived = false;

                while (!ackReceived) {
                    // Create Frame: "SEQ_NUM:DATA"
                    String frameMessage = currentSeqNum + ":" + frameData;
                    byte[] sendData = frameMessage.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receiverAddr, RECEIVER_PORT);

                    System.out.println("\n[SENDER] Sending: Frame " + currentSeqNum + " (Data: " + frameData + ")");
                    socket.send(sendPacket);

                    // Wait for ACK
                    byte[] receiveBuffer = new byte[1024];
                    DatagramPacket ackPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                    try {
                        socket.receive(ackPacket);
                        String ackStr = new String(ackPacket.getData(), 0, ackPacket.getLength());
                        
                        // Parse ACK (Expected format "ACK:X")
                        if (ackStr.startsWith("ACK:")) {
                            int ackNum = Integer.parseInt(ackStr.split(":")[1]);
                            if (ackNum == currentSeqNum) {
                                System.out.println("[SENDER] Success: Received ACK " + ackNum);
                                ackReceived = true;
                                currentSeqNum = 1 - currentSeqNum; // Toggle sequence number for next frame
                            } else {
                                System.out.println("[SENDER] Error: Received ACK for wrong frame (" + ackNum + ")");
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("[SENDER] *** TIMEOUT: ACK for Frame " + currentSeqNum + " not received! Retransmitting... ***");
                        // Loop will continue and resend the frame
                    }
                }
            }
            System.out.println("\n[SENDER] All frames sent successfully!");
        } catch (Exception e) {
            System.err.println("Sender error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
