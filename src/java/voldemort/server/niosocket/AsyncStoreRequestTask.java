package voldemort.server.niosocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.concurrent.Callable;

import voldemort.server.protocol.RequestHandler;
import voldemort.server.protocol.StreamRequestHandler;

/**
 * Asynchronous store request to parallelize on the disk IO, once the network IO
 * has completed.
 * 
 */
public class AsyncStoreRequestTask implements Callable<StreamRequestHandler> {

    DataInputStream inData;

    DataOutputStream outData;

    RequestHandler requestHandler;

    public AsyncStoreRequestTask(DataInputStream in, DataOutputStream out, RequestHandler handler) {
        inData = in;
        outData = out;
        requestHandler = handler;
    }

    public StreamRequestHandler call() throws Exception {
        return requestHandler.handleRequest(inData, outData);
    }
}
