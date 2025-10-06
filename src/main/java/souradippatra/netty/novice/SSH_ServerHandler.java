package souradippatra.netty.novice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles banner exchange with the client.
 */
public class SSH_ServerHandler extends ChannelInboundHandlerAdapter {

    private static final String SERVER_BANNER = "SSH-2.0-MySSHServer_0.1";

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("[SSH SERVER] 🔗 Client connected: " + ctx.channel().remoteAddress());
        // Send SSH banner immediately
        ctx.writeAndFlush(SERVER_BANNER + "\n");
        System.out.println("[SSH SERVER] 📤 Sent banner: " + SERVER_BANNER);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String clientBanner = msg.toString().trim();
        System.out.println("[SSH SERVER] 📥 Received client banner: " + clientBanner);

        // Close connection after banner exchange
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("[SSH SERVER] ❌ Client disconnected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("[SSH SERVER] ⚠️ Error: " + cause.getMessage());
        ctx.close();
    }
}
