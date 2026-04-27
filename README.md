# 📡 Stop-and-Wait ARQ Protocol Simulation (Java)

## 🚀 Overview

This project implements the **Stop-and-Wait Automatic Repeat reQuest (ARQ)** protocol using **Java UDP sockets**.

It simulates reliable data transmission over an unreliable network by:

* Sending one frame at a time
* Waiting for acknowledgment (ACK)
* Retransmitting frames if ACK is lost (simulated)

---

## 🧠 Key Concepts

* **Stop-and-Wait ARQ**
* **UDP Socket Programming**
* **Sequence Numbers (0 & 1)**
* **Timeout & Retransmission**
* **ACK Loss Simulation**

---

## 📁 Project Structure

```
stop_and_wait_arq/
│── Receiver.java
│── Sender.java
│── Receiver.class
│── Sender.class
```

---

## ⚙️ How It Works

### 🔵 Sender

* Sends frames in format: `SEQ_NUM:DATA`
* Waits for ACK from receiver
* If timeout occurs → retransmits frame
* Uses sequence numbers (0,1) to track frames

### 🟢 Receiver

* Receives frames
* Checks sequence number
* Sends ACK for correct frames
* Simulates ACK loss (30% probability)

---

## 🛠️ How to Run

### 1️⃣ Navigate to project folder

```bash
cd stop_and_wait_arq
```

### 2️⃣ Compile

```bash
javac *.java
```

### 3️⃣ Run Receiver (Terminal 1)

```bash
java Receiver
```

### 4️⃣ Run Sender (Terminal 2)

```bash
java Sender
```

---

## 📌 Sample Output

### Receiver:

```
[RECEIVER] Received: Frame 0 with data: 'Hello'
[RECEIVER] Frame 0 is correct. Processing...
[RECEIVER] Sent: ACK for Frame 0
```

### Sender:

```
[SENDER] Sending: Frame 0 (Data: Hello)
[SENDER] Success: Received ACK 0
```

---

## ⚠️ Notes

* Run **Receiver first**, then Sender
* Uses **localhost (127.0.0.1)** for communication
* ACK loss is simulated using randomness
* Timeout is set to **2 seconds**

---

## 🎯 Features

* Reliable transmission over UDP
* Duplicate frame detection
* Automatic retransmission
* Simple and clear implementation

---

## 📚 Use Cases

* Computer Networks Lab Experiments
* Understanding ARQ protocols
* Learning socket programming

---

## 👨‍💻 Author

**Aryan Khunt**

---

## ⭐ If you found this helpful

Give it a star ⭐ and share!
