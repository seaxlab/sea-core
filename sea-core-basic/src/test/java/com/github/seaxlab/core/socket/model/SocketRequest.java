package com.github.seaxlab.core.socket.model;

import com.github.seaxlab.core.enums.DateFormatEnum;
import com.github.seaxlab.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/6
 * @since 1.0
 */
@Slf4j
public class SocketRequest implements Runnable {
    /**
     * It stores the socket with the request to process
     */
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;

    /**
     * Private to hide it
     */
    private SocketRequest() {
    }

    /**
     * Constructor
     *
     * @param socket Socket with the request
     */
    public SocketRequest(Socket socket) {
        this.socket = socket;

        try {
            if (socket != null) {
                this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.output = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            }
        } catch (IOException ex) {
            log.error("socket error", ex);
        }
    }

    /**
     * Processes the request.
     */
    @Override
    public void run() {
        try {
            while (this.socket.isConnected()) {
                log.info("SOCKET SERVER: read...");
                String line = this.input.readLine();
                log.info("SOCKET SERVER: read {}", line);

                if (line != null) {
                    String response = DateUtil.toString(DateUtil.nowDate(), DateFormatEnum.yyyyMMddHHmmss);
                    this.output.write(response);
                    this.output.newLine();
                    this.output.flush();
                }

            }
        } catch (IOException ex) {
            log.error("SOCKET SERVER: Error.", ex);
        } catch (Exception ex) {
            log.error("SOCKET SERVER: Error.", ex);
        } finally {
            this.closeSocket();
        }
    }

    /**
     * Closes the Socket connection
     */
    private void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            log.error("SOCKET SERVER: Error cerrando el socket server", ex);
        }
    }
}
