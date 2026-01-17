class Buffer {
    int item;
    boolean available = false;

    synchronized void produce(int value) throws InterruptedException {
        while (available) {
            wait();
        }
        item = value;
        available = true;
        System.out.println("Produced: " + item);
        notify();
    }

    synchronized int consume() throws InterruptedException {
        while (!available) {
            wait();
        }
        available = false;
        System.out.println("Consumed: " + item);
        notify();
        return item;
    }
}

class Producer extends Thread {
    Buffer buffer;

    Producer(Buffer b) {
        buffer = b;
    }

    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                buffer.produce(i);
            }
        } catch (Exception e) {}
    }
}

class Consumer extends Thread {
    Buffer buffer;

    Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                buffer.consume();
            }
        } catch (Exception e) {}
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        Buffer b = new Buffer();
        new Producer(b).start();
        new Consumer(b).start();
    }
}
