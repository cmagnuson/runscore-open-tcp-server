package com.mtecresults.runscoreopentcpserver.controller;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class RunScoreOpenTCPServer {

    private static final Logger LOG = LoggerFactory.getLogger(RunScoreOpenTCPServer.class);

    final NioSocketAcceptor acceptor;
    final ServerDataHandler handler;
    final TCPMinaHandler minaHandler;
    private final SessionTrackingListener sessionTrackingListener = new SessionTrackingListener();

    public RunScoreOpenTCPServer(ServerDataHandler handler) throws IOException {
        this.handler = handler;
        minaHandler = new TCPMinaHandler(handler);
        LOG.info("RunScore Open server startup on port: {}", handler.getServerPort());

        acceptor = new NioSocketAcceptor();
        acceptor.setReuseAddress(true);
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(StandardCharsets.UTF_8);
        textLineCodecFactory.setDecoderMaxLineLength(64_000);
        textLineCodecFactory.setEncoderMaxLineLength(64_000);
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(textLineCodecFactory));
        acceptor.addListener(sessionTrackingListener);
        acceptor.setHandler(minaHandler);
        acceptor.bind( new InetSocketAddress(handler.getServerPort()) );
    }

    @SuppressWarnings("unused")
    public void stopServer() {
        LOG.info("RunScore Open server shutdown on port: {}", handler.getServerPort());
        acceptor.dispose();
    }

    @SuppressWarnings("unused")
    public ServerDataHandler getHandler() {
        return handler;
    }

    @SuppressWarnings("unused")
    public Set<IoSession> getActiveSessions() {
        return sessionTrackingListener.getActiveSessions();
    }
}
