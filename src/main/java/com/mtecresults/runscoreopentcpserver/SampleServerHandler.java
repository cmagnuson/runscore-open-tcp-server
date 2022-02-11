package com.mtecresults.runscoreopentcpserver;

import com.mtecresults.runscoreopentcpserver.controller.RunScoreOpenTCPServer;
import com.mtecresults.runscoreopentcpserver.controller.ServerDataHandler;
import com.mtecresults.runscoreopentcpserver.domain.Passing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class SampleServerHandler extends ServerDataHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SampleServerHandler.class);

    public static void main(String args[]) throws Exception {
        LOG.info("Sample server startup");
        new RunScoreOpenTCPServer(new SampleServerHandler());
    }

    @Override
    public void handlePassings(Collection<Passing> passings) {
        LOG.info("Passings message received");
        for(Passing passing: passings){
            LOG.info("\t"+passing);
        }
    }

    @Override
    public int getServerPort() {
        return 4001;
    }
}
