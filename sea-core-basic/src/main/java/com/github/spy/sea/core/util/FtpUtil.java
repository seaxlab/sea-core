package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;

import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;

/**
 * ftp util
 *
 * @author spy
 * @version 1.0 2020/8/27
 * @since 1.0
 */
@Slf4j
public final class FtpUtil {
    private FTPClient ftpClient;

    public FtpUtil() {
        ftpClient = new FTPClient();
        // 在控制台打印操作过程
//        mFTPClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    /**
     * 连接ftp服务器
     *
     * @param host     ip地址
     * @param port     端口号
     * @param username 账号
     * @param pwd      密码
     * @return 是否连接成功
     * @throws SocketException
     * @throws IOException
     */
    public boolean openConnection(String host, int port, String username, String pwd)
            throws SocketException, IOException {
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.connect(host, port);

        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.login(username, pwd);
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("system type={}", ftpClient.getSystemType());

                FTPClientConfig config = new FTPClientConfig(ftpClient.getSystemType().split(" ")[0]);
                config.setServerLanguageCode("zh");
                ftpClient.configure(config);
                return true;
            }
        }
        disConnection();
        return false;
    }

    /**
     * 登出并断开连接
     */
    public void logout() {
        log.info("ftp logout");
        if (ftpClient.isConnected()) {
            log.info("logout ok");
            try {
                ftpClient.logout();
                disConnection();
            } catch (IOException e) {
                log.error("ftp logout exception", e);
            }
        }
    }

    /**
     * 断开连接
     */
    private void disConnection() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("ftp dis connection exception.", e);
            }
        }
    }

    /**
     * 下载文件到本地地址
     *
     * @param remotePath 远程地址
     * @param localDir   本地地址
     * @throws IOException
     */
    public boolean downLoad(String remotePath, String localDir) throws IOException {
        // 进入被动模式
        ftpClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        ftpClient.setFileType(BINARY_FILE_TYPE);
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        if (ftpFiles == null || ftpFiles.length == 0) {
            log.info("远程文件不存在");
            return false;
        } else if (ftpFiles.length > 1) {
            log.info("远程文件是文件夹");
            return false;
        }
        long lRemoteSize = ftpFiles[0].getSize();
        // 本地文件的地址
        File localFileDir = new File(localDir);
        if (!localFileDir.exists()) {
            localFileDir.mkdirs();
        }
        File localFile = new File(localFileDir, ftpFiles[0].getName());
        long localSize = 0;
        FileOutputStream fos = null;
        if (localFile.exists()) {
            if (localFile.length() == lRemoteSize) {
                log.info("已经下载完毕");
                return true;
            } else if (localFile.length() < lRemoteSize) {
                // 要下载的文件存在，进行断点续传
                localSize = localFile.length();
                ftpClient.setRestartOffset(localSize);
                fos = new FileOutputStream(localFile, true);
            }
        }
        if (fos == null) {
            fos = new FileOutputStream(localFile);
        }
        InputStream is = ftpClient.retrieveFileStream(remotePath);
        byte[] buffers = new byte[1024];
        long step = lRemoteSize / 10;
        long process = localSize / step;
        int len = -1;
        while ((len = is.read(buffers)) != -1) {
            fos.write(buffers, 0, len);
            localSize += len;
            long newProcess = localSize / step;
            if (newProcess > process) {
                process = newProcess;
                log.info("下载进度:" + process);
            }
        }
        is.close();
        fos.close();
        boolean isDo = ftpClient.completePendingCommand();
        if (isDo) {
            log.info("下载成功");
        } else {
            log.info("下载失败");
        }
        return isDo;

    }

    /**
     * 创建远程目录
     *
     * @param remote 远程目录
     * @return 是否创建成功
     * @throws IOException
     */
    public boolean createDirectory(String remote) throws IOException {
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirctory = remote.substring(start, end);
                if (!ftpClient.changeWorkingDirectory(subDirctory)) {
                    if (ftpClient.makeDirectory(subDirctory)) {
                        ftpClient.changeWorkingDirectory(subDirctory);
                    } else {
                        log.error("创建目录失败");
                        return false;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                if (end <= start) {
                    break;
                }
            }
        }
        return true;
    }

    /**
     * rename file
     *
     * @param remoteFileName
     * @param newFileName
     * @return
     * @throws IOException
     */
    public boolean rename(String remoteFileName, String newFileName) throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        boolean status = false;
        FTPFile[] files = ftpClient.listFiles(remoteFileName);
        if (files.length == 1) {
            status = ftpClient.rename(remoteFileName, newFileName);
        } else {
            log.warn("file not exist");
        }
        log.info("FTP服务器文件名更新标识：{}", status);
        return status;
    }

    /**
     * delete file
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public boolean delete(String filePath) throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        FTPFile[] files = ftpClient.listFiles(filePath);
        boolean status = false;
        if (files.length == 1) {
            status = ftpClient.deleteFile(filePath);
        } else {
            log.error("file not exist");
        }
        log.info("FTP服务器文件删除标识：{}", status);
        return status;
    }


    /**
     * 上传的文件
     *
     * @param remotePath 上传文件的路径地址（文件夹地址）
     * @param localPath  本地文件的地址
     * @throws IOException 异常
     */
    public boolean upload(String remotePath, String localPath) throws IOException {
        // 进入被动模式
        ftpClient.enterLocalPassiveMode();
        // 以二进制进行传输数据
        ftpClient.setFileType(BINARY_FILE_TYPE);
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            log.info("本地文件不存在");
            return false;
        }
        String fileName = localFile.getName();
        if (remotePath.contains("/")) {
            boolean isCreateOk = createDirectory(remotePath);
            if (!isCreateOk) {
                log.error("文件夹创建失败");
                return false;
            }
        }

        // 列出ftp服务器上的文件
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        long remoteSize = 0l;
        String remoteFilePath = remotePath + "/" + fileName;
        if (ftpFiles.length > 0) {
            FTPFile mFtpFile = null;
            for (FTPFile ftpFile : ftpFiles) {
                if (ftpFile.getName().endsWith(fileName)) {
                    mFtpFile = ftpFile;
                    break;
                }
            }
            if (mFtpFile != null) {
                remoteSize = mFtpFile.getSize();
                if (remoteSize == localFile.length()) {
                    log.info("文件已经上传成功");
                    return true;
                }
                if (remoteSize > localFile.length()) {
                    if (!ftpClient.deleteFile(remoteFilePath)) {
                        log.error("服务端文件操作失败");
                    } else {
                        boolean isUpload = uploadFile(remoteFilePath, localFile, 0);
                        log.info("是否上传成功：" + isUpload);
                    }
                    return true;
                }
                if (!uploadFile(remoteFilePath, localFile, remoteSize)) {
                    log.info("文件上传成功");
                    return true;
                } else {
                    // 断点续传失败删除文件，重新上传
                    if (!ftpClient.deleteFile(remoteFilePath)) {
                        log.error("服务端文件操作失败");
                    } else {
                        boolean isUpload = uploadFile(remoteFilePath, localFile, 0);
                        log.error("是否上传成功：{}", isUpload);
                    }
                    return true;
                }
            }
        }

        boolean isUpload = uploadFile(remoteFilePath, localFile, remoteSize);
        log.info("是否上传成功：{}", isUpload);
        return isUpload;
    }

    /**
     * 上传文件
     *
     * @param remoteFile 包含文件名的地址
     * @param localFile  本地文件
     * @param remoteSize 服务端已经存在的文件大小
     * @return 是否上传成功
     * @throws IOException
     */
    private boolean uploadFile(String remoteFile, File localFile, long remoteSize) throws IOException {
        long step = localFile.length() / 10;
        long process = 0;
        long readByteSize = 0;
        RandomAccessFile randomAccessFile = new RandomAccessFile(localFile, "r");
        OutputStream os = ftpClient.appendFileStream(remoteFile);
        if (remoteSize > 0) {
            // 已经上传一部分的时候就要进行断点续传
            process = remoteSize / step;
            readByteSize = remoteSize;
            randomAccessFile.seek(remoteSize);
            ftpClient.setRestartOffset(remoteSize);
        }
        byte[] buffers = new byte[1024];
        int len = -1;
        while ((len = randomAccessFile.read(buffers)) != -1) {
            os.write(buffers, 0, len);
            readByteSize += len;
            long newProcess = readByteSize / step;
            if (newProcess > process) {
                process = newProcess;
                log.info("当前上传进度为：{}", process);
            }
        }
        os.flush();
        randomAccessFile.close();
        os.close();
        boolean result = ftpClient.completePendingCommand();
        return result;
    }

//    /**
//     * 上传文件到ftp服务器
//     */
//    public static boolean ftpUpload(String fileName, String ftpUrl, int ftpPort,
//                                    String ftpUsername, String ftpPassword,
//                                    String ftpLocalDir, String ftpRemotePath) {
//        boolean result = false;
//        try {
//            boolean isConnection = openConnection(ftpUrl, ftpPort, ftpUsername, ftpPassword);
//            if (isConnection) {
//                boolean isSuccess = upload(ftpRemotePath, ftpLocalDir + "/" + fileName);
//                if (isSuccess) {
//                    log.info("文件上传成功！");
//                    result = true;
//                } else {
//                    log.info("文件上传失败！");
//                    result = false;
//                }
//                logout();
//            } else {
//                log.warn("链接ftp服务器失败，请检查配置信息是否正确！");
//                result = false;
//            }
//
//        } catch (Exception e) {
//            log.error("ftp exception.", e);
//        }
//        return result;
//    }
//
//    /**
//     * 从ftp服务器下载文件到本地
//     */
//    public static boolean ftpDownload(String fileName, String ftpUrl, int ftpPort,
//                                      String ftpUsername, String ftpPassword,
//                                      String ftpRemotePath, String ftpDownDir) {
//        boolean result = false;
//        try {
//            boolean isConnection = openConnection(ftpUrl, ftpPort, ftpUsername, ftpPassword);
//            if (isConnection) {
//                boolean isDownloadOk = downLoad(fileName, ftpDownDir);
//                boolean isCreateOk = createDirectory(ftpRemotePath, mFTPClient);
//                if (isDownloadOk && isCreateOk) {
//                    log.info("文件下载成功！");
//                    result = true;
//                } else {
//                    log.info("文件下载失败！");
//                    result = false;
//                }
//                logout();
//            } else {
//                log.info("链接ftp服务器失败，请检查配置信息是否正确！");
//                result = false;
//            }
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

}
