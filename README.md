![Release](https://jitpack.io/v/cmagnuson/runscore-open-tcp-server.svg)
(https://jitpack.io/#cmagnuson/runscore-open-tcp-server)

# runscore-open-tcp-server

Java TCP server for handling RunScore Open TCP feed.  Just override ServerDataHandler and create a new RunScoreOpenTCPServer with that handler.

```
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
    public String getServerName() {
        return "SampleServer";
    }

    @Override
    public int getServerPort() {
        return 4001;
    }
}
```

Easiest to integrate with your project using jitpack https://jitpack.io
```    
implementation 'com.github.cmagnuson:runscore-open-tcp-server:TAG' 
```

For Android exclude slf4j and include Android version:   
``` 
implementation('com.github.cmagnuson:runscore-open-tcp-server:TAG'){
    exclude group: 'org.slf4j'
}
implementation 'org.slf4j:slf4j-android:1.7.25'
```
