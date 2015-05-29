package com.claro.cpymes.ejb;

import java.io.FileNotFoundException;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.claro.cpymes.dto.FTPDTO;
import com.claro.cpymes.ejb.remote.SFTPRemote;
import com.claro.cpymes.util.Constant;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


@Stateless
@LocalBean
public class SFTPEJB implements SFTPRemote {

   private static final String KEY_CHECKING = "StrictHostKeyChecking";

   private static final String NO = "no";

   private Session session;

   private Channel channel;

   private ChannelSftp channelSftp;

   private JSch jsch;

   private FTPDTO ftpDTO;

   public void download() throws JSchException, SftpException, FileNotFoundException {
      crearSession();
      crearCanal();
      getFile();
      desconectar();
   }

   private void getFile() throws SftpException {
      String filePathFTP = getPathFileFtp();
      String filePathServer = getPathFileServer();
      channelSftp.get(filePathFTP, filePathServer);
   }

   private String getPathFileFtp() {
      return Constant.FTP_WORKING_DIR + Constant.NAME_FILE_NITS;
   }

   private String getPathFileServer() {
      return Constant.SERVER_WORKING_DIR + Constant.NAME_FILE_NITS;
   }

   private void crearSession() throws JSchException {
      ftpDTO = new FTPDTO();
      jsch = new JSch();
      session = jsch.getSession(ftpDTO.getUserName(), ftpDTO.getServer(), ftpDTO.getPort());
      session.setPassword(ftpDTO.getPassword());
      Properties config = new Properties();
      config.put(KEY_CHECKING, NO);
      session.setConfig(config);
      session.connect();
   }

   private void crearCanal() throws JSchException, SftpException {
      channel = session.openChannel(ftpDTO.getProtocol());
      channel.connect();
      channelSftp = (ChannelSftp) channel;
      channelSftp.cd(ftpDTO.getWorkingDir());
   }

   private void desconectar() {
      channelSftp.exit();
      session.disconnect();

   }

}