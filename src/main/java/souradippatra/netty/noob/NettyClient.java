package souradippatra.netty.noob;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Level 1: Basic Netty TCP Client
 * -------------------------------
 * - Connects to localhost:7575
 * - Sends a message to the server
 * - Receives echo and prints it
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7575;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInitializer(false));

            System.out.println("🚀 [CLIENT] Connecting to " + HOST + ":" + PORT);

            Channel channel = bootstrap.connect(HOST, PORT).sync().channel();

            // Send test message
            String message = "Hello from client!";
            System.out.println("📤 [CLIENT] Sending: " + message);
            channel.writeAndFlush(message + "\n");

            // Keep alive for 5 seconds to receive echo
            Thread.sleep(5000);

            channel.closeFuture().sync();

        } finally {
            System.out.println("🛑 [CLIENT] Closing connection...");
            group.shutdownGracefully();
            System.out.println("[CLIENT] ✅ Shutdown complete.");
        }
    }
}
