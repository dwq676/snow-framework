package com.zoe.snow.log.appender.mongo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import org.slf4j.Marker;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class MongoDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {

    }
   /* private boolean initialized = false;
    private MongoCollection<Document> collection = null;
    private MongoClient mongoClient = null;
    private WriteConcern writeConcern = WriteConcern.UNACKNOWLEDGED;
    private String collectionName = "logbackMongo";

    // @Value("${snow.mongo.host-name:192.168.2.39}")
    private String hostname = "192.168.2.39";
    // @Value("${snow.mongo.port}:27017")
    private String port = "27017";
    // @Value("${snow.mongo.username}")
    private String userName ;
    // @Value("${snow.mongo.database-name}:logback")
    private String databaseName = "logbackMongo";
    // @Value("${snow.mongo.password}")
    private String password;

    public MongoDBAppender() {
    }

    @Override
    public void start() {
        try {
            connect();
            super.start();
        } catch (UnknownHostException | MongoException e) {
            addError("Can't connect to mongo: host=" + hostname + ", port=" + port, e);
        }
    }

    @Override
    protected void append(ILoggingEvent evt) {
        if (evt == null || this.initialized == false)
            return; // just in case
        String level = String.valueOf(evt.getLevel());
        Document document = getBasicLog(evt);
        try {
            logException(evt.getThrowableProxy(), document);
            collection.insertOne(document);
        } catch (Exception e) {
            try {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                document.put("logging_error", "Could not log all the event information: " + sw.toString());
                collection.insertOne(document);
            } catch (Exception e2) { // really not working
                addError("Could not insert log to mongo: " + evt, e2);
            }
        }
    }

    private void connect() throws UnknownHostException {
        try {
            if (this.mongoClient != null) {
                this.close();
            }

            List e = this.getServerAddresses(this.hostname, this.port);
            // this.mongoClient = new MongoClient("192.168.2.39", 27017);
            this.mongoClient = this.getMongo(e, getMongoCredential(), getConfOptions());
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
            this.initialized = true;
        } catch (Exception e) {
            this.addError("Unexpected exception while initialising MongoDbAppender.", e);
        }
    }

    protected List<MongoCredential> getMongoCredential() {
        if (this.userName != null && this.userName.trim().length() > 0) {
            ArrayList mongoCredentials = new ArrayList();
            mongoCredentials.add(MongoCredential.createCredential(userName, databaseName, password.toCharArray()));
            return mongoCredentials;
        } else {
            return null;
        }
    }

    protected MongoClient getMongo(List<ServerAddress> addresses, List<MongoCredential> credentialsList, MongoClientOptions options) {
        if (credentialsList == null) {
            return addresses.size() < 2 ? new MongoClient((ServerAddress) addresses.get(0), options) : new MongoClient(addresses, options);
        } else {
            return addresses.size() < 2 ? new MongoClient((ServerAddress) addresses.get(0), credentialsList, options)
                    : new MongoClient(addresses, credentialsList, options);
        }
    }

    private List<ServerAddress> getServerAddresses(String hostname, String port) {
        ArrayList addresses = new ArrayList();
        String[] hosts = hostname.split(" ");
        String[] ports = port.split(" ");
        if (ports.length != 1 && ports.length != hosts.length) {
            this.addError("MongoDB appender port property must contain one port or a port per host");
        } else {
            List portNums = this.getPortNums(ports);
            if (portNums.size() != 1 && portNums.size() != hosts.length) {
                this.addError("MongoDB appender port property must contain one port or a valid port per host");
            } else {
                boolean onePort = portNums.size() == 1;
                int i = 0;
                String[] arr$ = hosts;
                int len$ = hosts.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String host = arr$[i$];
                    int portNum = (onePort ? (Integer) portNums.get(0) : (Integer) portNums.get(i)).intValue();

                    try {
                        addresses.add(new ServerAddress(host.trim(), portNum));
                    } catch (RuntimeException var15) {
                        this.addError("MongoDB appender hostname property contains unknown host", var15);
                    }
                    ++i;
                }
            }
        }
        return addresses;
    }

    private List<Integer> getPortNums(String[] ports) {
        ArrayList portNums = new ArrayList();
        String[] arr$ = ports;
        int len$ = ports.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String port = arr$[i$];

            try {
                Integer e = Integer.valueOf(port.trim());
                if (e.intValue() < 0) {
                    this.addError("MongoDB appender port property can\'t contain a negative integer");
                } else {
                    portNums.add(e);
                }
            } catch (NumberFormatException var8) {
                this.addError("MongoDB appender can\'t parse a port property value into an integer", var8);
            }
        }
        return portNums;
    }

    private void close() {
        if (this.mongoClient != null) {
            this.collection = null;
            this.mongoClient.close();
        }
    }

    private MongoClientOptions getConfOptions() {
        return new MongoClientOptions.Builder().socketKeepAlive(false) // 是否保持长链接
                .connectTimeout(0) // 链接超时时间
                .socketTimeout(5000) // read数据超时时间
                .connectionsPerHost(1) // 每个地址最大请求数
                .maxWaitTime(0) // 长链接的最大等待时间
                .threadsAllowedToBlockForConnectionMultiplier(1)// 一个socket最大的等待请求数
                .writeConcern(writeConcern).build();
    }

    private Document getBasicLog(ILoggingEvent evt) {
        Document document = new Document();
        document.put("t", new Date(evt.getTimeStamp()));
        document.put("ThreadID", Thread.currentThread().getId());
        Document docChild = new Document();
        docChild.put("logger", evt.getLoggerName());
        Marker m = evt.getMarker();
        if (m != null) {
            docChild.put("marker", m.getName());
        }
        Map<String, String> mdcMap = evt.getMDCPropertyMap();
        if (!mdcMap.isEmpty()) {
            Document mdcDoc = new Document();
            // Try to save a numeric value as Int
            Iterator<Map.Entry<String, String>> it = mdcMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = it.next();
                mdcDoc.put(pairs.getKey(), pairs.getValue());
                if (isNumeric(pairs.getValue())) {
                    try {
                        Integer intValue = Integer.parseInt(pairs.getValue());
                        mdcDoc.put(pairs.getKey(), intValue);
                    } catch (NumberFormatException nfe) {
                        mdcDoc.put(pairs.getKey(), pairs.getValue());
                    }
                } else {
                    mdcDoc.put(pairs.getKey(), pairs.getValue());
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
            docChild.put("mdc", docChild);
        }
        docChild.put("message", evt.getFormattedMessage());

        document.put("d", docChild);
        return document;
    }

    private void logException(IThrowableProxy tp, Document document) {
        if (tp == null)
            return;
        String tpAsString = ThrowableProxyUtil.asString(tp); // the stack trace
                                                             // basically
        List<String> stackTrace = Arrays.asList(tpAsString.replace("\t", "").split(CoreConstants.LINE_SEPARATOR));
        if (stackTrace.size() > 0) {
            Document docD = (Document) document.get("d");
            docD.put("exception", stackTrace.get(0));
            if (stackTrace.size() > 1) {
                docD.put("stacktrace", stackTrace.subList(1, stackTrace.size()));
            }
            document.put("d", docD);
        }
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    @Override
    public void stop() {
        this.close();
        super.stop();
    }*/

}
