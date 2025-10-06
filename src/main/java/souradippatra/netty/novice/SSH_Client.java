package souradippatra.netty.novice;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Level 2: SSH Handshake Simulation Client
 * ----------------------------------------
 * - Connects to localhost:7575
 * - Receives server banner
 * - Sends its own banner
 */
public class SSH_Client {

    private final String host = "127.0.0.1";
    private final int port = 7575;

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new io.netty.handler.codec.LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new io.netty.handler.codec.string.StringDecoder());
                            ch.pipeline().addLast(new io.netty.handler.codec.string.StringEncoder());

                             ch.pipeline().addLast(new SSH_ClientHandler());
                         }
                     });

            System.out.println("[SSH CLIENT] 🚀 Connecting to " + host + ":" + port);
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().sync();
        } finally {
            System.out.println("[SSH CLIENT] 📴 Shutting down gracefully...");
            group.shutdownGracefully();
            System.out.println("[SSH CLIENT] ✅ Shutdown complete.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SSH_Client().start();
    }
}
