package com.claro.cpymes.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.claro.cpymes.dto.FTPDTO;
import com.claro.cpymes.ejb.remote.SFTRemote;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


@Stateless
@LocalBean
public class SFTPEJB implements SFTRemote {

   private static final String KEY_CHECKING = "StrictHostKeyChecking";

   private static final String NO = "no";

   private Session session;

   private Channel channel;

   private ChannelSftp channelSftp;

   private JSch jsch;

   private FTPDTO ftpDTO;

   public void upLoad(String fileNamePath) throws JSchException, SftpException, FileNotFoundException {

      crearSession();
      crearCanal();
      File f = new File(fileNamePath);
      channelSftp.put(new FileInputStream(f), f.getName());
      desconectar();

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