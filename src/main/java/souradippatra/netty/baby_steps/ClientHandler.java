package souradippatra.netty.baby_steps;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("✅ [CLIENT] Connected to server.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("❌ [CLIENT] Disconnected from server.");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        String message = msg.toString().trim();
        System.out.println("[CLIENT] 📥 Received from server: " + message);
        System.out.println("[CLIENT] 📴 Closing connection...");
        ctx.close();
        // ⚠ Do NOT echo back — client just logs
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("⚠️ [CLIENT ERROR] " + cause.getMessage());
        ctx.close();
    }
}
