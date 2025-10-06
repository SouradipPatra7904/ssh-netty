package souradippatra.netty.novice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SSH_ClientHandler extends ChannelInboundHandlerAdapter {

    private static final String CLIENT_BANNER = "SSH-2.0-MySSHClient_0.1";

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("[SSH CLIENT] ✅ Connected to server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String serverBanner = msg.toString().trim();
        System.out.println("[SSH CLIENT] 📥 Received server banner: " + serverBanner);

        // Send client banner
        ctx.writeAndFlush(CLIENT_BANNER + "\n");
        System.out.println("[SSH CLIENT] 📤 Sent banner: " + CLIENT_BANNER);

        // Close connection after banner exchange
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("[SSH CLIENT] ⚠️ Error: " + cause.getMessage());
        ctx.close();
    }
}
