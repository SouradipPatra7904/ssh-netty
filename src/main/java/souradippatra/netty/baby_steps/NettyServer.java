package souradippatra.netty.baby_steps;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Level 1: Basic Netty TCP Server
 * --------------------------------
 * - Listens on port 7575
 * - Accepts incoming connections
 * - Logs connection lifecycle events
 * - Echoes back received messages
 */
public class NettyServer {

    private static final int PORT = 7575;

    public static void main(String[] args) throws InterruptedException {
        // Boss group accepts connections
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // Worker group handles the actual traffic
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChannelInitializer(true))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("🚀 [SERVER] Starting Netty Server on port " + PORT);

            // Bind server to port
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("✅ [SERVER] Listening on port " + PORT);

            // Wait until server socket is closed
            future.channel().closeFuture().sync();

        } finally {
            System.out.println("🛑 [SERVER] Shutting down...");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("[SERVER] ✅ Shutdown complete.");
        }
    }
}
