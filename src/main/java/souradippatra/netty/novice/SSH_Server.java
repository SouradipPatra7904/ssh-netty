package souradippatra.netty.novice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Level 2: SSH Handshake Simulation Server
 * ----------------------------------------
 * - Listens on port 7575
 * - Sends SSH banner upon connection
 * - Receives client banner and logs it
 */
public class SSH_Server {

    private final int port = 7575;

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new io.netty.handler.codec.LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new io.netty.handler.codec.string.StringDecoder());
                            ch.pipeline().addLast(new io.netty.handler.codec.string.StringEncoder());
                            
                            ch.pipeline().addLast(new SSH_ServerHandler());
                        }
                    });

            System.out.println("[SSH SERVER] 🚀 Starting on port " + port);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("[SSH SERVER] ✅ Listening for connections");

            future.channel().closeFuture().sync();
        } finally {
            System.out.println("[SSH SERVER] 📴 Shutting down gracefully...");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("[SSH SERVER] ✅ Shutdown complete.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SSH_Server().start();
    }
}
