package com.mtecresults.runscoreopentcpserver.controller;

import com.mtecresults.runscoreopentcpserver.domain.Passing;
import org.apache.mina.core.session.DummySession;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TCPMinaHandlerTest extends ServerDataHandler {

    private List<Passing> passingsReceived;
    private TCPMinaHandler handler;

    @Before
    public void setUp() {
        passingsReceived = new ArrayList<>();
        handler = new TCPMinaHandler(this);
    }

    @Test
    public void messageReceived() throws Exception {
        handler.messageReceived(new DummySession(), "RSBCI,412,14:34:00.039,ANNOUNCER");
        handler.messageReceived(new DummySession(), "RSBCI,400,14:34:24.720,START+FINISH");
        assertEquals(2, passingsReceived.size());

        Passing p1 = passingsReceived.get(0);
        assertEquals("RSBCI", p1.getBibOrChip());
        assertEquals("412", p1.getChipcode());
        assertEquals(52440039, p1.getTimeMillis());
        assertEquals("ANNOUNCER", p1.getLocationName());

        Passing p2 = passingsReceived.get(1);
        assertEquals("RSBCI", p2.getBibOrChip());
        assertEquals("400", p2.getChipcode());
        assertEquals(52464720, p2.getTimeMillis());
        assertEquals("START+FINISH", p2.getLocationName());
    }

    @Override
    public void handlePassings(Collection<Passing> passings) {
        passingsReceived.addAll(passings);
    }

    @Override
    public int getServerPort() {
        return 0;
    }
}