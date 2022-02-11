package com.mtecresults.runscoreopentcpserver.controller;

import com.mtecresults.runscoreopentcpserver.domain.Passing;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Collections;

public class TCPMinaHandler extends IoHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TCPMinaHandler.class);

    private final ServerDataHandler handler;

    public TCPMinaHandler(ServerDataHandler handler){
        super();
        this.handler = handler;
        LOG.debug("New TCPMinaHandler starting up");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        LOG.warn("Exception caught in session: "+session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String line = message.toString();
        LOG.trace("Message received: "+line);

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(new StringReader(line));
        records.forEach(record -> {
            if(record.size() < 4){
                LOG.warn("Invalid Record Passed: {}", record);
            } else {
                try {
                    String bibOrChip = record.get(0);
                    String chip = record.get(1);
                    long time = ParseUtils.parseTimeString(record.get(2));
                    String location = record.get(3);
                    Passing passing = Passing.builder().bibOrChip(bibOrChip).chipcode(chip).timeMillis(time).locationName(location).build();
                    handler.handlePassings(Collections.singletonList(passing));
                }
                catch(ParseException pe){
                    LOG.error("Failed to parse time for passing: {}", record);
                }
            }
        });
    }
}
