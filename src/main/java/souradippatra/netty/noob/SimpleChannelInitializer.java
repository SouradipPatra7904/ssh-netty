package souradippatra.netty.noob;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Channel Initializer for both Client and Server
 * ----------------------------------------------
 * The constructor accepts a boolean flag to decide whether
 * this pipeline is for a SERVER or a CLIENT.
 */
public class SimpleChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final boolean isServer;

    public SimpleChannelInitializer(boolean isServer) {
        this.isServer = isServer;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        if (isServer) {
            pipeline.addLast(new ServerHandler());
        } else {
            pipeline.addLast(new ClientHandler());
        }
    }
}
