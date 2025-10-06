package souradippatra.netty.noob;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("🔗 [SERVER] Connection from " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("❌ [SERVER] Connection closed: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        String message = msg.toString().trim();
        System.out.println("[SERVER] 📥 Received: " + message);

        String response = "Echo: " + message;
        ctx.writeAndFlush(response + "\n").addListener(ChannelFuture -> {
            System.out.println("[SERVER] 📤 Replied: " + response);
            ctx.close(); // close connection after one reply
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("⚠️ [SERVER ERROR] " + cause.getMessage());
        ctx.close();
    }
}
