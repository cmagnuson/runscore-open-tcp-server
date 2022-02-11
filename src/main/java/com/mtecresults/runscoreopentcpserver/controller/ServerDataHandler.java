package com.mtecresults.runscoreopentcpserver.controller;
import com.mtecresults.runscoreopentcpserver.domain.Passing;

import java.util.Collection;

public abstract class ServerDataHandler {

    public abstract void handlePassings(Collection<Passing> passings);

    public abstract int getServerPort();
}
