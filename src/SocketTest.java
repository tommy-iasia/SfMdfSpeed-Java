import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SocketTest extends SpeedTest {
    @Override
    public Collection<SpeedResult> run() throws IOException, InterruptedException {
        final var results = new LinkedList<SpeedResult>();

        final int port = 20123;
        final var server = new ServerSocket(port);

        final var bytes = TestData.repeat((byte)51, 600_000_000);

        for (int i = 0; i < 10; i++) {
            final var result = run(server, bytes);
            results.add(result);
        }

        server.close();

        return results;
    }

    private SpeedResult run(final ServerSocket server, final byte[] sendBytes) throws InterruptedException, IOException {
        final var receiver = new Box<Socket>();
        final var acceptThread = new Thread(() -> {
            try {
                Socket accepted = server.accept();

                synchronized (server) {
                    receiver.setValue(accepted);

                    server.notify();
                }
            } catch (IOException e) {
                throw new TestException(e);
            }
        });
        acceptThread.start();

        final Socket sender = new Socket("127.0.0.1", server.getLocalPort());

        while (true) {
            synchronized (server) {
                if (receiver.getValue() != null) {
                    break;
                }

                server.wait(1);
            }
        }

        final var startTime = System.nanoTime();

        final var sent = new Box<Boolean>(false);
        final var sendThread = new Thread(() -> {
            try {
                try (final var stream = sender.getOutputStream()) {
                    stream.write(sendBytes, 0, sendBytes.length);
                    stream.flush();
                }

                synchronized (server) {
                    sent.setValue(true);
                    server.notify();
                }
            } catch (final IOException e) {
                throw new TestException(e);
            }
        });
        sendThread.start();

        final var received = new Box<>(false);
        final var receiveThread = new Thread(() -> {
            try {
                try (final var stream = receiver.getValue().getInputStream()) {
                    final var receiveBytes = stream.readAllBytes();

                    if (sendBytes.length != receiveBytes.length) {
                        throw new Exception();
                    }
                }

                synchronized (server) {
                    received.setValue(true);
                    server.notify();
                }
            } catch (final Exception e) {
                throw new TestException(e);
            }
        });
        receiveThread.start();

        while (true) {
            synchronized (server) {
                if (sent.getValue() && received.getValue()) {
                    break;
                }

                server.wait(1);
            }
        }

        sender.close();
        receiver.getValue().close();

        final var endTime = System.nanoTime();
        final var elapsed = (double)(endTime - startTime) / 1_000_000_000;

        return new SpeedResult("Tcp Socket Sends and Receives", sendBytes.length, elapsed);
    }
}
